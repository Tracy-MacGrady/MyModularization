package com.toughen.libs.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 麦迪
 * @数据库工具类
 * @使用过程中需先调用本类的setInitDBHelper方法
 */

public class SQLiteDataBaseTool {
    private static boolean isBeginTransaction = false;
    private static volatile SQLiteDataBaseTool sqLiteDataBaseTool;
    private MyDataBaseHelper dbHelper;
    private DBManagerTool dbManagerTool;

    /**
     * @说明 实例化SQLiteDataBaseTool
     * @说明 使用过程中需先调用本类的setInitDBHelper方法
     */
    public static SQLiteDataBaseTool getInstance() {
        if (sqLiteDataBaseTool == null) {
            synchronized (SQLiteDataBaseTool.class) {
                sqLiteDataBaseTool = new SQLiteDataBaseTool();
            }
        }
        return sqLiteDataBaseTool;
    }

    private SQLiteDataBaseTool() {
        super();
        setDbManagerTool();//初始化DB管理类
    }

    /**
     * @param dataBaseName 数据库名称
     * @param version      数据库版本号
     * @说明 初始化DBHelper对象的实例
     */
    public void setInitDBHelper(Context context, String dataBaseName, int version) {
        dbHelper = new MyDataBaseHelper(context, dataBaseName, null, version);
    }

    /**
     * @param tablename
     * @说明 初始化数据库表
     */
    public <T> void setDbHelperInitTable(String tablename, Class<T> tClass) {
        getDbManagerTool().close();
        createTable(tablename, tClass);
    }

    private synchronized void setDbManagerTool() {
        dbManagerTool = new DBManagerTool();
    }

    private DBManagerTool getDbManagerTool() {
        if (dbManagerTool == null) {
            setDbManagerTool();
        }
        return dbManagerTool;
    }

    /**
     * @param table 数据库表名
     * @param datas 插入的数据
     * @return 数据库数据插入状态（是否成功插入）-1为失败
     */
    public <T> long insert_Data(String table, List<T> datas, Class<T> tClass) {
        if (!getDbManagerTool().tabIsExist(table)) {
            if (datas != null && datas.size() > 0) {
                LogUtils.e("come in the method inster_Data");
                createTable(table, tClass);
            }
        }
        boolean index = false;
        try {
            beginTransaction();
            for (int j = 0; j < datas.size(); j++) {
                ContentValues values = obj2ContentValues(datas.get(j));
                if (values != null) {
                    long num = getDbManagerTool().getDB().insert(table, null, values);
                    if (num < 0) {
                        index = true;
                        throw new Exception();
                    }
                }
            }
            commitDBTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDBTransaction();
            if (!index) {
                return 0;
            }
        }
        return -1;
    }

    /**
     * @param table
     * @param where
     * @param where_val
     * @return 数据库数据删除状态（是否成功删除）-1为失败
     */
    public long delete_Data(String table, String where, String[] where_val) {
        if (!getDbManagerTool().tabIsExist(table)) return -1;
        beginTransaction();
        int num = getDbManagerTool().getDB().delete(table, where, where_val);
        commitDBTransaction();
        closeDBTransaction();
        if (num > 0) {
            return num;
        }
        return -1;
    }

    /**
     * @param table     数据库表名
     * @param data      修改的数据
     * @param where     修改的条件
     * @param where_val 修改条件的值
     * @return 修改数据库中的数据的状态（是否修改成功）-1为失败
     */
    public <T> long update_Data(String table, T data, String where,
                                String[] where_val) {
        if (!getDbManagerTool().tabIsExist(table)) return -1;
        ContentValues values = obj2ContentValues(data);
        if (values != null) {
            beginTransaction();
            long num = getDbManagerTool().getDB().update(table, values, where,
                    where_val);
            commitDBTransaction();
            closeDBTransaction();
            return num;
        }
        return -1;
    }

    /**
     * @param sql        数据库的查询sql语句
     * @param paramValue 数据库的查询sql语句中对应的条件
     * @return 从数据库中查询到的数据List<"BaseEntity">
     */
    public <T> List<T> select_Data(String sql, String[] paramValue, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        Cursor cursorRef = getDbManagerTool().getDB().rawQuery(sql, paramValue);
        while (cursorRef.moveToNext()) {
            try {
                T en = (T) tClass.newInstance();
                String[] name_vals = cursorRef.getColumnNames();
                Field[] fields = tClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    for (int i = 0; i < name_vals.length; i++) {
                        String value = cursorRef.getString(cursorRef
                                .getColumnIndex(name_vals[i]));
                        if (name_vals[i].equals(field.getName())) {
                            String type = field.getType().toString();
                            if (type.equals("int") || type.equals("Integer")) {
                                int int_val = Integer.parseInt(value);
                                field.set(en, int_val);
                            } else if (type.equals("boolean") || type.equals("Boolean")) {
                                boolean boolVal = Boolean.parseBoolean(value);
                                field.set(en, boolVal);
                            } else {
                                field.set(en, value);
                            }
                        }
                    }
                    field.setAccessible(false);
                }
                list.add(en);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } finally {
                if (cursorRef != null) {
                    cursorRef.close();
                }
            }
        }
        if (list != null && list.size() > 0) return list;
        return null;
    }

    /**
     * @param obj 传入的原始值
     * @将object类型的数据转换成contentvalues类型的数据
     */
    private ContentValues obj2ContentValues(Object obj) {
        ContentValues con_val = new ContentValues();
        Field[] names = obj.getClass().getDeclaredFields();
        if (names.length == 0) return null;
        try {
            for (int j = 0; j < names.length; j++) {
                names[j].setAccessible(true);
                con_val.put(names[j].getName(), names[j].get(obj).toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return con_val;
    }

    // 开启事务
    private void beginTransaction() {
        getDbManagerTool().beginTransaction();
    }

    //结束事务并关闭
    private void closeDBTransaction() {
        getDbManagerTool().endTransaction();
    }

    //提交事务
    private void commitDBTransaction() {
        getDbManagerTool().commit();
    }

    //@param drop 是否删除数据库中的表
    public void deleteTable(String tableName, boolean drop) {
        if (dbHelper != null) {
            dbHelper.deleteTable(getDbManagerTool().getDB(), tableName, drop);
        }
    }

    public void createTable(String tableName, Class tableType) {
        if (dbHelper != null) {
            dbHelper.createTable(getDbManagerTool().getDB(), tableName, tableType);
        }
    }

    /**
     * @author 麦迪
     * @说明 数据库事务管理类
     * @说明 打开、提交、关闭数据库
     */
    class DBManagerTool {
        private ThreadLocal<SQLiteDatabase> tlLocal = new ThreadLocal<SQLiteDatabase>();

        public SQLiteDatabase getDB() {
            SQLiteDatabase db = tlLocal.get();
            if (db == null) {
                if (dbHelper != null) {
                    db = dbHelper.getWritableDatabase();
                }
                tlLocal.set(db);
            }
            return db;
        }

        /**
         * 判断某张表是否存在
         *
         * @param tabName 表名
         * @return
         */
        public boolean tabIsExist(String tabName) {
            boolean result = false;
            if (tabName == null) {
                return false;
            }
            SQLiteDatabase db = getDB();
            Cursor cursor = null;
            try {
                String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "'";
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToNext()) {
                    int count = cursor.getInt(0);
                    if (count > 0) {
                        result = true;
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return result;
        }

        public void beginTransaction() {
            isBeginTransaction = true;
            getDB().beginTransaction();
        }

        public void commit() {
            if (tlLocal.get() != null) {
                tlLocal.get().setTransactionSuccessful();
            }
        }

        public void endTransaction() {
            if (tlLocal.get() != null) {
                tlLocal.get().endTransaction();
                isBeginTransaction = false;
            }
        }

        public void close() {
            if (tlLocal.get() != null) {
                if (isBeginTransaction) {
                    endTransaction();
                }
                tlLocal.get().close();
                tlLocal.set(null);
            }
        }
    }

    /**
     * @author 麦迪
     * @说明 数据库工具类中的私有类MyDataBasHelper
     * @说明 创建表
     */
    private class MyDataBaseHelper extends SQLiteOpenHelper {
        private String sql = null;

        private MyDataBaseHelper(Context context, String name,
                                 CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase arg0) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        public void deleteTable(SQLiteDatabase db, String tableName, boolean drop) {
            if (drop) {
                db.execSQL("drop table if exists " + tableName);
            }
        }

        public void createTable(SQLiteDatabase db, String tableName, Class classVal) {
            sql = getSqlVal(tableName, classVal);
            if (sql != null) {
                db.execSQL(sql);
            }
        }

        /**
         * @param tableName
         * @param classVal
         * @return
         * @说明 获取sql语句
         */
        private String getSqlVal(String tableName, Class classVal) {
            String val = "create table if not exists ";
            val += tableName.split("\\.")[0] + " (";
            Field[] fields = classVal.getDeclaredFields();
            if (fields != null) {
                for (int i = fields.length - 1; i >= 0; i--) {
                    String type = fields[i].getType().toString();
                    String name = fields[i].getName();
                    if (type.equals("int") || type.equals("Integer")) {
                        val = val.replace(");", ",");
                        val += name + " integer not null); ";
                    } else {
                        val = val.replace(");", ",");
                        val += name + " text not null); ";
                    }
                }
            }
            if (fields.length == 0) val = null;
            return val;
        }
    }
}

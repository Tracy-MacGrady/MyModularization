package com.qingguo.downloadlib.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DownloadDBDao {
    protected void createTable(SQLiteDatabase db) {
        String sql = getCreateTableSQL(DownloadDBEntity.class);
        db.execSQL(sql);
    }

    protected void dropTable(SQLiteDatabase db) {
        String sql = getDropTableSQL(DownloadDBEntity.class);
        db.execSQL(sql);
    }

    public long insert(SQLiteDatabase db, DownloadDBEntity entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("DOWNLOADPATH", entity.getDownloadPath());
        contentValues.put("FILESAVEPATH", entity.getFileSavePath());
        contentValues.put("FILESAVENAME", entity.getFileSaveName());
        contentValues.put("THREADID", entity.getThreadID());
        contentValues.put("DOWNLOADLENGTH", entity.getDownloadLength());
        contentValues.put("ENDLOCATION", entity.getEndLocation());
        contentValues.put("STARTLOCATION", entity.getStartLocation());
        return db.insert(DownloadDBEntity.class.getSimpleName().toUpperCase(), null, contentValues);
    }

    public long delete(SQLiteDatabase db, DownloadDBEntity entity) {
        return db.delete(DownloadDBEntity.class.getSimpleName().toUpperCase(), "DOWNLOADPATH=? AND THREADID=? AND FILESAVENAME=?", new String[]{entity.getDownloadPath(), entity.getThreadID() + "", entity.getFileSaveName()});
    }

    public long update(SQLiteDatabase db, DownloadDBEntity entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("DOWNLOADLENGTH", entity.getDownloadLength());
        return db.update(DownloadDBEntity.class.getSimpleName().toUpperCase(), contentValues, "DOWNLOADPATH=? AND THREADID=? AND FILESAVENAME=?", new String[]{entity.getDownloadPath(), entity.getThreadID() + "", entity.getFileSaveName()});
    }

    public List<DownloadDBEntity> selectList(SQLiteDatabase db, String downloadPath, String saveName) {
        List<DownloadDBEntity> list = new ArrayList<>();
        Cursor cursor = db.query(DownloadDBEntity.class.getSimpleName().toUpperCase(), null, "DOWNLOADPATH=? AND FILESAVENAME=?", new String[]{downloadPath, saveName}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                DownloadDBEntity entity = new DownloadDBEntity();
                entity.setDownloadPath(cursor.getString(cursor.getColumnIndex("DOWNLOADPATH")));
                entity.setEndLocation(cursor.getInt(cursor.getColumnIndex("ENDLOCATION")));
                entity.setDownloadLength(cursor.getInt(cursor.getColumnIndex("DOWNLOADLENGTH")));
                entity.setFileSavePath(cursor.getString(cursor.getColumnIndex("FILESAVEPATH")));
                entity.setFileSaveName(cursor.getString(cursor.getColumnIndex("FILESAVENAME")));
                entity.setThreadID(cursor.getInt(cursor.getColumnIndex("THREADID")));
                entity.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                entity.setStartLocation(cursor.getInt(cursor.getColumnIndex("STARTLOCATION")));
                list.add(entity);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public String getCreateTableSQL(Class tableCalss) {
        if (tableCalss == null) return null;
        String tableName = tableCalss.getSimpleName().toUpperCase();
        StringBuffer SQL = new StringBuffer("CREATE TABLE IF NOT EXISTS ").append(tableName);
        Field[] fields = tableCalss.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            SQL.append("(");
            for (int i = 0; i < fields.length; i++) {
                String type = fields[i].getType().toString();
                String name = fields[i].getName();
                if ("int".equalsIgnoreCase(type) || "integer".equalsIgnoreCase(type))
                    type = "INTEGER";
                else type = "TEXT";
                SQL.append(" ").append(name.toUpperCase()).append(" ").append(type);
                if ("id".equalsIgnoreCase(name)) SQL.append(" PRIMARY KEY AUTOINCREMENT");
                SQL.append(" NOT NULL").append(i == fields.length - 1 ? ");" : ",");
            }
        }
        return SQL.toString();
    }

    public String getDropTableSQL(Class tableCalss) {
        if (tableCalss == null) return null;
        String tableName = tableCalss.getSimpleName().toUpperCase();
        StringBuffer SQL = new StringBuffer("DROP TABLE IF EXISTS ").append(tableName);
        return SQL.toString();
    }

}

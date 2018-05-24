package com.zx.toughen.database.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.toughen.libs.database.BaseDao;
import com.zx.toughen.database.entity.DB_MessageEntity;

public class DB_MessageDao extends BaseDao {
    @Override
    protected void createTable(SQLiteDatabase db) {
        String sql = getCreateTableSQL(DB_MessageEntity.class);
        db.execSQL(sql);
    }

    @Override
    protected void dropTable(SQLiteDatabase db) {
        String sql = getDropTableSQL(DB_MessageEntity.class);
        db.execSQL(sql);
    }

    public boolean insertData(DB_MessageEntity entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("", "");
        contentValues.put("", "");
        contentValues.put("", "");
        contentValues.put("", "");
        boolean insertError = getLocalDB().insert("", "", null) == -1;
        return insertError;
    }
}

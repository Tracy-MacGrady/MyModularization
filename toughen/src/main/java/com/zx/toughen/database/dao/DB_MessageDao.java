package com.zx.toughen.database.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.toughen.libs.database.BaseDao;
import com.zx.toughen.database.entity.DB_MessageEntity;

public class DB_MessageDao extends BaseDao<DB_MessageEntity> {
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

    @Override
    protected boolean insert(DB_MessageEntity insertEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("", "");
        contentValues.put("", "");
        contentValues.put("", "");
        contentValues.put("", "");
        boolean insertError = getLocalDB().insert("", "", null) == -1;
        return insertError;
    }

    @Override
    protected boolean delete(DB_MessageEntity entity) {
        return false;
    }

    @Override
    protected boolean update(DB_MessageEntity entity) {
        return false;
    }

    @Override
    protected DB_MessageEntity selectOne(String selectSql) {
        return null;
    }

    @Override
    protected DB_MessageEntity selectAll() {
        return null;
    }
}

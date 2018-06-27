package com.toughen.libs.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static List<BaseDao> TABLES = new ArrayList();

    public MySQLiteOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
    }

    public MySQLiteOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, dbName, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < TABLES.size(); i++) {
            TABLES.get(i).createTable(db);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        for (int i = 0; i < TABLES.size(); i++) {
            TABLES.get(i).setLocalThreadDB(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = 0; i < TABLES.size(); i++) {
        }
    }

    public static void addTables(BaseDao dao) {
        TABLES.add(dao);
    }
}

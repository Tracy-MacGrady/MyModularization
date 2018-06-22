package com.qingguo.downloadlib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DownloadDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "download.db";

    public DownloadDBHelper(Context context) {
        this(context, DB_NAME, null, 1);
    }

    public DownloadDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (DownloadDBManager.getInstance().getDownloadDBDao() == null)
            DownloadDBManager.getInstance().setDownloadDBDao(new DownloadDBDao());
        DownloadDBManager.getInstance().getDownloadDBDao().createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DownloadDBManager.getInstance().getDownloadDBDao() != null) {
            DownloadDBManager.getInstance().getDownloadDBDao().dropTable(db);
            DownloadDBManager.getInstance().getDownloadDBDao().createTable(db);
        }
    }
}

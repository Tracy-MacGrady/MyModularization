package com.toughen.libs.download.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class DownloadDBManager {
    private static volatile DownloadDBManager instance;
    private DownloadDBDao downloadDBDao;
    private SQLiteDatabase db;

    private DownloadDBManager() {
    }

    public static DownloadDBManager getInstance() {
        if (instance == null) {
            synchronized (DownloadDBManager.class) {
                if (instance == null) instance = new DownloadDBManager();
            }
        }
        return instance;
    }

    public synchronized void init(Context context) {
        downloadDBDao = new DownloadDBDao();
        db = new DownloadDBHelper(context).getWritableDatabase();
    }

    public synchronized void destory() {
        if (downloadDBDao != null) downloadDBDao = null;
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public synchronized long insert(DownloadDBEntity entity) {
        if (downloadDBDao == null) return -1;
        return downloadDBDao.insert(db, entity);
    }


    public synchronized long delete(DownloadDBEntity entity) {
        if (downloadDBDao == null) return -1;
        return downloadDBDao.delete(db, entity);
    }

    public synchronized long update(DownloadDBEntity entity) {
        if (downloadDBDao == null) return -1;
        return downloadDBDao.update(db, entity);
    }

    public synchronized List<DownloadDBEntity> selectList(String downloadPath) {
        if (downloadDBDao == null) return null;
        return downloadDBDao.selectList(db, downloadPath);
    }

    public DownloadDBDao getDownloadDBDao() {
        return downloadDBDao;
    }

    public void setDownloadDBDao(DownloadDBDao dao) {
        this.downloadDBDao = dao;
    }
}

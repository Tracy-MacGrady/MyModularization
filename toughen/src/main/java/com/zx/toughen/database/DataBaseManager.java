package com.zx.toughen.database;

public class DataBaseManager {
    private static volatile DataBaseManager instance;

    private DataBaseManager() {
    }

    public static DataBaseManager getInstance() {
        if (instance == null) {
            synchronized (DataBaseManager.class) {
                if (instance == null) instance = new DataBaseManager();
            }
        }
        return instance;
    }
}

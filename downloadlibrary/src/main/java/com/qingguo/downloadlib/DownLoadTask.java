package com.qingguo.downloadlib;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.qingguo.downloadlib.database.DownloadDBEntity;
import com.qingguo.downloadlib.database.DownloadDBManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownLoadTask {
    private int max_thread_num = 1;
    private List<DownloadThread> threads = new ArrayList<>();
    private String downloadPath;
    private String fileSavePath;
    private String saveName;
    private Handler handler;
    private int fileLength;

    public DownLoadTask(String downloadUrl, String savePath, String saveName) {
        this.downloadPath = downloadUrl;
        this.fileSavePath = savePath;
        this.saveName = saveName;
    }

    public void stopTask() {
        for (int i = 0, size = threads.size(); i < size; i++) {
            threads.get(i).stopDownload();
        }
    }

    public void startTask() {
        fileLength = 0;
        threads.clear();
        List<DownloadDBEntity> dbEntities = DownloadDBManager.getInstance().selectList(downloadPath, saveName);
        if (dbEntities == null || dbEntities.size() == 0) {
            createDownloadInfo();
        } else {
            for (int i = 0, size = dbEntities.size(); i < size; i++) {
                DownloadDBEntity entity = dbEntities.get(i);
                threads.add(new DownloadThread(entity));
                fileLength += entity.getThreadSaveFileLength();
            }
            startThread();
        }
    }

    private void startThread() {
        if (fileLength < 0) {
            Message msg = Message.obtain();
            msg.what = DownloadConstant.DOWNLOAD_FILE_NOT_EXIT;
            msg.obj = "fileLength==" + fileLength;
            handler.sendMessage(msg);
        } else {
            Message msg = Message.obtain();
            msg.what = DownloadConstant.DOWNLOAD_GET_FILELENGTH;
            msg.obj = fileLength;
            handler.sendMessage(msg);
            for (int i = 0, size = threads.size(); i < size; i++) {
                DownloadThread thread = threads.get(i);
                if (thread.getEntity() != null) {
                    if (!thread.getEntity().isDownloadFinished()) {
                        thread.setHandler(handler);
                        thread.start();
                    } else {
                        DownloadDBManager.getInstance().delete(thread.getEntity());
                        Message msg2 = Message.obtain();
                        msg2.what = DownloadConstant.DOWNLOAD_FINISH;
                        handler.sendMessage(msg2);
                    }
                }
            }
        }
    }

    private void createDownloadInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(downloadPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    if (connection.getResponseCode() == 302) {
                        String headerLocation = connection.getHeaderField("Location");
                        connection.disconnect();
                        if (!TextUtils.isEmpty(downloadPath)) {
                            url = new URL(headerLocation);
                            connection = (HttpURLConnection) url.openConnection();
                        }
                    }
                    int fileLength = connection.getContentLength();
                    connection.disconnect();
                    Message msg = Message.obtain();
                    msg.what = 100;
                    msg.obj = fileLength;
                    getFileLenhandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler getFileLenhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                fileLength = (int) msg.obj;
                int downloadSize = fileLength / max_thread_num;
                for (int i = 0; i < max_thread_num; i++) {
                    DownloadDBEntity entity = new DownloadDBEntity();
                    if (i == max_thread_num - 1) {
                        entity.setEndLocation(fileLength);
                    } else {
                        entity.setEndLocation((i + 1) * downloadSize);
                    }
                    entity.setFileSaveName(saveName);
                    entity.setDownloadLength(0);
                    entity.setDownloadPath(downloadPath);
                    entity.setStartLocation(i * downloadSize);
                    entity.setFileSavePath(fileSavePath);
                    entity.setThreadID(i);
                    DownloadDBManager.getInstance().insert(entity);
                    threads.add(new DownloadThread(entity));
                }//05-25 18:45:12.101 5003-5084/com.qingguo.antcallservice D/DownloadThread: 线程_0_正在下载【开始位置 : 0，结束位置：10366684】
                startThread();
            }
        }
    };

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getSaveName() {
        return saveName;
    }
}

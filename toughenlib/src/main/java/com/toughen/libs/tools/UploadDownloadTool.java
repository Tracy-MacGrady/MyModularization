package com.toughen.libs.tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @多线程下载
 * @断点续传
 **/
public class UploadDownloadTool {
    private class DownLoad_Thread implements Runnable {
        private HttpURLConnection http;// http请求实例（连接到服务器）
        private String path;// 下载地址
        private int threadLength;// 单个线程下载长度
        private int downLoadLength;// 单个线程已下载长度
        private int threadNum;// 下载线程编号

        @Override
        public void run() {
            try {
                http = (HttpURLConnection) new URL(path).openConnection();
                http.setConnectTimeout(5000);// 设置http请求超时时间
                http.setRequestMethod("GET");
                http.setRequestProperty(
                        "Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
                http.setRequestProperty("Accept-Language", "zh-CN");
                http.setRequestProperty("Referer", this.path.toString());
                http.setRequestProperty("Charset", "UTF-8");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.toughen.libs.tools;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author 麦迪
 * @文件读取、写入工具类
 */
public class FileWRUtils {
    private static String TAG = FileWRUtils.class.getSimpleName();
    private volatile static FileWRUtils instance;

    private FileWRUtils() {
    }

    /**
     * @return
     * @说明 实例化FileWRTool
     */
    public static FileWRUtils getInstance() {
        if (instance == null) {
            synchronized (FileWRUtils.class) {
                if (instance == null) instance = new FileWRUtils();
            }
        }
        return instance;
    }

    /**
     * @param path
     * @param isAppend
     * @param val
     * @return
     * @说明 向本地写入文件
     */
    public boolean fileWrite(String path, boolean isAppend, String val) {
        try {
            File file = new File(path);
            if (!fileExit(file)) {
                createFile(file);
            }
            FileOutputStream outStream = new FileOutputStream(file, isAppend);
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream);
            BufferedWriter bufferWriter = new BufferedWriter(outStreamWriter);
            bufferWriter.write(val);
            bufferWriter.flush();
            bufferWriter.close();
            outStreamWriter.close();
            outStream.close();
            return true;
        } catch (FileNotFoundException e) {
            LogUtils.e(TAG, "文件找不到，文件创建失败");
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.e(TAG, "文件流错误");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 存储图片到本地
     *
     * @param savePath
     * @param name
     * @param imagePath
     * @return
     */
    public boolean saveImage(String savePath, String name, String imagePath) {
        try {
            File file = new File(savePath, name);
            if (!fileExit(file)) createFile(file);
            FileOutputStream outStream = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(outStream);
            Bitmap bitmap = ImageUtils.getCompressBitmap(imagePath, 480,
                    800);
            bitmap = ImageUtils.getRotateImage(bitmap, imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (FileNotFoundException e) {
            LogUtils.e(TAG, "文件找不到，文件创建失败");
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.e(TAG, "文件流错误");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断文件是否存在
     *
     * @param file
     * @return
     */
    private boolean fileExit(File file) {
        if (file.exists()) {
            LogUtils.e(TAG, "文件存在");
            return true;
        }
        LogUtils.e(TAG, "文件不存在");
        return false;
    }

    /**
     * 创建文件目录
     *
     * @param file
     */
    private void createFile(File file) {
        if (!fileExit(file)) {
            file.mkdir();
            LogUtils.e(TAG, "创建文件目录成功");
        }
    }
}


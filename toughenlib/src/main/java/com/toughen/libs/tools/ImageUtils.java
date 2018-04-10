package com.toughen.libs.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {

    /**
     * 获取旋转后的图片
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static Bitmap getRotateImage(Bitmap bitmap, String path) {
        int degrees = readPictureDegree(path);
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degrees);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
        }
        return bitmap;
    }

    /**
     * 获取图片角度
     *
     * @param path 图片地址
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int val = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (val) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap getCompressBitmap(Bitmap bitmap) {
        int compressVal = 100;//压缩比例 例如compressVal=60是压缩率，表示压缩40%;如果不压缩是100，
        int imgSize = 100;//单位kb
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressVal, baos);
        while (baos.toByteArray().length / 1024 > imgSize) {
            compressVal -= 10;
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressVal, baos);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    /**
     * 获取比例压缩后的图片
     *
     * @param path
     * @return
     */
    public static Bitmap getCompressBitmap(String path, int w_px, int h_px) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = getSampleSize(options, w_px, h_px);
        options.inJustDecodeBounds = false;
        return getCompressBitmap(BitmapFactory.decodeFile(path, options));
    }

    /**
     * 获取图片压缩比例
     *
     * @param options
     * @param w
     * @param h
     * @return
     */
    private static int getSampleSize(BitmapFactory.Options options, int w, int h) {
        int inSampleSize = 1;
        int height = options.outHeight;
        int width = options.outWidth;
        if (height > h || width > w) {
            int heightRatio = Math.round(height / h);
            int widthRatio = Math.round(width / w);
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}

package com.toughen.libs.tools;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 麦迪
 * @加密/编码/解码工具类
 */
public class Base64MD5Utils {
    /**
     * @param val
     * @param is_32bit
     * @param num
     * @param charval
     * @return MD5加密后的数据
     */
    public static String getMD5EncodeVal(String val, boolean is_32bit,
                                         int num, String charval) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] bytes = md5.digest();
            int a;
            StringBuffer stringbuf = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                a = bytes[i];
                if (a < 0) {
                    a += 256;
                }
                if (a < num) {
                    if (charval == null) {
                        charval = "a";
                    }
                    stringbuf.append(charval);
                }
                stringbuf.append(Integer.toHexString(a));
            }
            if (stringbuf.length() > 0) {
                if (!is_32bit) {
                    return stringbuf.substring(8, 24);
                }
                return stringbuf.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param val
     * @return Base64编码的数据
     */
    public static String getBase64EncodeVal(String val) {
        String value = Base64.encodeToString(val.getBytes(), Base64.DEFAULT);
        return value;
    }

    /**
     * @param val
     * @return Base64解码的数据
     */
    public static String getBase64DecodeVal(String val) {
        String value = new String(Base64.decode(val, Base64.DEFAULT));
        return value;
    }
}

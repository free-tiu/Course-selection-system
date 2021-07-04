package com.course.utils;

import java.security.MessageDigest;

/**
 * 功能描述：
 *      对指定字符串进行MD5加密
 * @author: Zhao
 * @Description: com.course.utils
 * @date: 2021/4/25
 */

public class MD5 {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**对指定字符串进行MD5加密
     * @param oldString:原始字符串，即待加密字符串
     * @return MD5加密后的字符串
     */
    public static String MD5Encode(String oldString) {
        String resultString = null;
        try {
            resultString=new String(oldString);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString=byteArrayToHexString(md.digest(resultString.getBytes()));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static String Md5_16(String result ) {
        return result= MD5Encode(result).substring(8,24);//16位的加密
    }

    public static void  main(String[] args){
        System.out.println(MD5Encode("eE@/@##17"));
    }
}

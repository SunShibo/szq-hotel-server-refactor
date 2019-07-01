package com.szq.hotel.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 *
 * @author jason
 *
 */
public class MD5Util {

    static MessageDigest md;
    static {
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (Exception ex) {
        }
    }

    /**
     * 加密方法
     *
     * @param msg
     * @return
     */
    public static String digest(String msg) {
        byte[] rlt = md.digest(msg.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rlt.length; i++) {
            String d = "00" + Integer.toHexString(rlt[i]);
            sb.append(d.substring(d.length() - 2));
        }
        return sb.toString();
    }


    /***
     * MD5加密 生成32位md5码
     * @param inStr 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }




    public static void main(String[] args) throws Exception {
        String str=md5Encode("quanquan");
        System.out.println(str);
        System.out.println(str.length());
        System.out.println(md5Encode(str));

   }
}

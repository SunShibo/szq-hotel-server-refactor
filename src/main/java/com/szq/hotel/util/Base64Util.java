package com.szq.hotel.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {

    public static String  decode(final byte[] bytes) {
        byte[] bytes1 = Base64.decodeBase64(bytes);
        return new String(bytes1
        );
    }

    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }



    public static void main(String[] args ){
        String str="测试加密字符串";
        String encode = encode(str.getBytes());
        System.out.println("加密后的字符串:"+encode);
     //   String decode=decode(encode.getBytes());
       // System.out.println("解密后的字符串:"+decode);
    }
}

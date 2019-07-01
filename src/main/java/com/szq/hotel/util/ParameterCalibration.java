package com.szq.hotel.util;

public class ParameterCalibration {



    //验证参数
    public static  boolean  verification (Object... obj){
        if(obj==null)
            return false;
        for(int i=0;i<obj.length;i++) {
            if(obj[i]==null)
                return false;

            if(obj[i] instanceof  String) {
                String str=(String)obj[i];
                if(str.trim().length()<1)
                    return false;
            }
        }

        return true;

    }

    //其中有一个不为null就为true
    public static boolean atLeastSingleton(Object... obj){
        if(obj==null)
            return false;

        for(int i=0;i<obj.length;i++) {
            if(obj[i] !=null && obj[i] instanceof  String) {
                String str=(String)obj[i];
                if(str.trim().length()>0)
                    return true;
            }

            if(obj[i]!=null)
                return true;
        }

        return false;

    }

    public static boolean atLeastSingletonObj(Object  o,Object... obj){
        if(o==null)
            return false;
        else
            return atLeastSingleton(obj);
    }

}

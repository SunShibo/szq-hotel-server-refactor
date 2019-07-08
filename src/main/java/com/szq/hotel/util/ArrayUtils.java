package com.szq.hotel.util;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/31.
 */
public class ArrayUtils {
    public static final String DEFAULT_SPLIT = ",";

    public ArrayUtils() {
    }

    public static boolean isEmpty(Object[] obj) {
        return null == obj || obj.length == 0;
    }

    public static String join(Object[] obj) {
        return join(obj, ",");
    }

    public static String join(Object[] obj, String split) {
        StringBuffer sb = new StringBuffer();
        if(null != obj && obj.length > 0) {
            for(int i = 0; i < obj.length; ++i) {
                sb.append(obj[i]);
                if(i != obj.length - 1) {
                    sb.append(split);
                }
            }
        }

        return sb.toString();
    }

    public static void asd(Integer st,Integer end){
                for (int i = st; i <= end; i++) {
                    String str = String.format("%012d", i);
                    //0代表前面补零，3代表输出3位，根据需要修改即可。
                    System.out.println(str);
                }
            }



    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println(join(list.toArray(), ","));
        asd(15,20);
    }
}

package com.szq.hotel.util;

import java.util.ArrayList;
import java.util.List;

public class IntegerUtil {
    public static List<Integer> returnList(Integer a, Integer b){
        List<Integer> list = new ArrayList<Integer>();
        if(a<b){
            Integer t = b-a;
            for(int i=0;i<=t;i++){
                list.add(a);
                System.out.println(a);
                a++;
            }
        }

        return list;
    }
    public static void main(String[] args) {
    		returnList(5,5);
    }
}

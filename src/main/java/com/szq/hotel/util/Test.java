package com.szq.hotel.util;

public class Test {
    public static void main(String[] hehe){
        boolean[]  a={false,false,false,true,true,true,true};
        boolean[]  b={true,true,true,false,false,false,true};
        for (int i = 0; i <=6; i++) {
            int x=0;
            if(i==0){
                x=6;
            }else{
                x=i-1;
            }
            if(((a[i]==false && a[x]==true) || (a[i]==true && a[x]==false) ) && ((b[i]==false && b[x]==true) || (b[i]) ==true && b[x]==false )){
                System.out.println("今天是星期:"+(i+1));
            }
        }
    }
}

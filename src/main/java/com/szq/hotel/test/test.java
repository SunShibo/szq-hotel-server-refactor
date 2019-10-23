package com.szq.hotel.test;

import com.alibaba.fastjson.JSON;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.OrderController;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class test {
    public static void main(String args[]) throws ParseException {
        Date time=new Date();
        //获取当天日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        System.out.println(hour);
        if (hour <= 4) {
            calendar.add(Calendar.DATE, -1);
        }
        Date myTime = calendar.getTime();
        SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currDate = simp.parse(simp.format(myTime));
        System.out.println("myTime:"+simp.format(myTime));
        //获取当天开始时间
        Calendar start4 = Calendar.getInstance();
        start4.setTime(time);
        start4.set(Calendar.HOUR_OF_DAY, 04);
        start4.set(Calendar.MINUTE, 0);
        start4.set(Calendar.SECOND, 0);
        Date startTime = start4.getTime();
        System.out.println("startTime:"+simp.format(startTime));
        //获取当天结束时间
        Calendar close4 = Calendar.getInstance();
        close4.setTime(time);
        close4.set(Calendar.HOUR_OF_DAY, 04);
        close4.set(Calendar.MINUTE, 0);
        close4.set(Calendar.SECOND, 0);
        close4.add(Calendar.DATE, 1);
        Date endTime = close4.getTime();
        System.out.println("endTime:"+simp.format(endTime));

    }
}

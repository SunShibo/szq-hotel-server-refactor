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
        BigDecimal bigDecimal=new BigDecimal(1);
        bigDecimal.add(new BigDecimal(2));
        System.err.println(bigDecimal);

    }
}

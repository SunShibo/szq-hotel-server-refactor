package com.szq.hotel.entity.bo;

/**
 * @Author: Bin Wang
 * @date: Created in 10:18 2019/7/26
 */
public class HotelTableBO {

    private  String day = "0";//今天
    private  String month = "0";//本月
    private  String lastMonthDay = "0";//上月同期
    private  String year = "0";//本年累计
    private  String LastYearDay = "0";//去年同期
    private  String insertRial = "0";//增长率

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLastMonthDay() {
        return lastMonthDay;
    }

    public void setLastMonthDay(String lastMonthDay) {
        this.lastMonthDay = lastMonthDay;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLastYearDay() {
        return LastYearDay;
    }

    public void setLastYearDay(String lastYearDay) {
        LastYearDay = lastYearDay;
    }

    public String getInsertRial() {
        return insertRial;
    }

    public void setInsertRial(String insertRial) {
        this.insertRial = insertRial;
    }
}

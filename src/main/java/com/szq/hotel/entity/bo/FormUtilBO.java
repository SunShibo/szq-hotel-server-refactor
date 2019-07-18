package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

public class FormUtilBO extends BaseModel {
    private String day="0";
    private  String month="0";
    private  String year="0";
    private  String LastYear="0";
    private  String lastYearMonth="0";
    private String  lastYearDay="0";

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLastYear() {
        return LastYear;
    }

    public void setLastYear(String lastYear) {
        LastYear = lastYear;
    }

    public String getLastYearMonth() {
        return lastYearMonth;
    }

    public void setLastYearMonth(String lastYearMonth) {
        this.lastYearMonth = lastYearMonth;
    }

    public String getLastYearDay() {
        return lastYearDay;
    }

    public void setLastYearDay(String lastYearDay) {
        this.lastYearDay = lastYearDay;
    }
}

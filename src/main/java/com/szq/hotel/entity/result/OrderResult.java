package com.szq.hotel.entity.result;

import java.util.Date;

//在住 预离报表
public class OrderResult {
    private String orderNumber;//订单号
    private String name;//入住人姓名
    private String value;//入住人证件类型
    private String certificateNumber;//证件号
    private String roomName;//房号
    private String roomTypeName;//房型
    private String money;//当天价格
    private Date startTime;//入住日期
    private Date endTime;//结束日期
    private String payCashNum;//现金支付金额
    private String otherPayNum;//其他支付金额
    private String roomRate;//房费
    private String otherRate;//其他费用
    private String freeRateNum;//免单费用

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPayCashNum() {
        return payCashNum;
    }

    public void setPayCashNum(String payCashNum) {
        this.payCashNum = payCashNum;
    }

    public String getOtherPayNum() {
        return otherPayNum;
    }

    public void setOtherPayNum(String otherPayNum) {
        this.otherPayNum = otherPayNum;
    }

    public String getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(String roomRate) {
        this.roomRate = roomRate;
    }

    public String getOtherRate() {
        return otherRate;
    }

    public void setOtherRate(String otherRate) {
        this.otherRate = otherRate;
    }

    public String getFreeRateNum() {
        return freeRateNum;
    }

    public void setFreeRateNum(String freeRateNum) {
        this.freeRateNum = freeRateNum;
    }
}

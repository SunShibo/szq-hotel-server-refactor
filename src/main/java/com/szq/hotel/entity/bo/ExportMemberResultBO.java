package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.util.Date;

public class ExportMemberResultBO extends BaseModel {
    private Integer id;
    private String  cardNumber;//卡号
    private String hotelName;//注册店
    private String name;//姓名
    private String memberLevelName;//会员级别
    private String birthday; //生日
    private String phone;//手机号
    private String memberDiscount;// 折扣
    private String payCount;//消费合计
    private String sumStoreValue;//储值总额
    private String storeValueBalance;//储值余额
    private String sumIntegral;//累计积分
    private String conversionIntegral;//已兑积分
    private String integralBalance;//剩余积分
    private String salesman; //销售员
    private Date  sellingTime;//发卡日期
    private String  certificateType;//证件类型
    private String  certificateNumber;//证件号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberLevelName() {
        return memberLevelName;
    }

    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberDiscount() {
        return memberDiscount;
    }

    public void setMemberDiscount(String memberDiscount) {
        this.memberDiscount = memberDiscount;
    }

    public String getPayCount() {
        return payCount;
    }

    public void setPayCount(String payCount) {
        this.payCount = payCount;
    }

    public String getSumStoreValue() {
        return sumStoreValue;
    }

    public void setSumStoreValue(String sumStoreValue) {
        this.sumStoreValue = sumStoreValue;
    }

    public String getStoreValueBalance() {
        return storeValueBalance;
    }

    public void setStoreValueBalance(String storeValueBalance) {
        this.storeValueBalance = storeValueBalance;
    }

    public String getSumIntegral() {
        return sumIntegral;
    }

    public void setSumIntegral(String sumIntegral) {
        this.sumIntegral = sumIntegral;
    }

    public String getConversionIntegral() {
        return conversionIntegral;
    }

    public void setConversionIntegral(String conversionIntegral) {
        this.conversionIntegral = conversionIntegral;
    }

    public String getIntegralBalance() {
        return integralBalance;
    }

    public void setIntegralBalance(String integralBalance) {
        this.integralBalance = integralBalance;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public Date getSellingTime() {
        return sellingTime;
    }

    public void setSellingTime(Date sellingTime) {
        this.sellingTime = sellingTime;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }
}

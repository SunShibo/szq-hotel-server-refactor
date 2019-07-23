package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class IncomeBO extends BaseModel {
    private Integer id;//营业收入
    private String roomRate;//房费
    private String timeoutRoomRate;//超时房费
    private String roomRateAdjustment;//房费调整
    private String otherRate;//其他费用
    private String commodity;//商品
    private String compensation;//赔偿
    private String memberCardRate;//会员卡收入
    private String debtSum;//借方总记
    private String cash;//现金
    private String bankCard;//银行卡
    private String wechat;//微信
    private String alipay;//支付宝
    private String onlinePay;//线上支付
    private String creditSum;//贷方总记
    private String nightAuditorTime;//夜审时间
    private String hotelId;//酒店id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(String roomRate) {
        this.roomRate = roomRate;
    }

    public String getTimeoutRoomRate() {
        return timeoutRoomRate;
    }

    public void setTimeoutRoomRate(String timeoutRoomRate) {
        this.timeoutRoomRate = timeoutRoomRate;
    }

    public String getRoomRateAdjustment() {
        return roomRateAdjustment;
    }

    public void setRoomRateAdjustment(String roomRateAdjustment) {
        this.roomRateAdjustment = roomRateAdjustment;
    }

    public String getOtherRate() {
        return otherRate;
    }

    public void setOtherRate(String otherRate) {
        this.otherRate = otherRate;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public String getMemberCardRate() {
        return memberCardRate;
    }

    public void setMemberCardRate(String memberCardRate) {
        this.memberCardRate = memberCardRate;
    }

    public String getDebtSum() {
        return debtSum;
    }

    public void setDebtSum(String debtSum) {
        this.debtSum = debtSum;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getOnlinePay() {
        return onlinePay;
    }

    public void setOnlinePay(String onlinePay) {
        this.onlinePay = onlinePay;
    }

    public String getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(String creditSum) {
        this.creditSum = creditSum;
    }

    public String getNightAuditorTime() {
        return nightAuditorTime;
    }

    public void setNightAuditorTime(String nightAuditorTime) {
        this.nightAuditorTime = nightAuditorTime;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
}

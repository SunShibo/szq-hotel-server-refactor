package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class IncomeBO extends BaseModel {
    private Integer id;//营业收入
    private BigDecimal roomRate;//房费
    private BigDecimal timeoutRoomRate;//超时房费
    private BigDecimal roomRateAdjustment;//房费调整
    private BigDecimal otherRate;//其他费用
    private BigDecimal commodity;//商品
    private BigDecimal compensation;//赔偿
    private BigDecimal memberCardRate;//会员卡收入
    private BigDecimal debtSum;//借方总记
    private BigDecimal cash;//现金
    private BigDecimal bankCard;//银行卡
    private BigDecimal wechat;//微信
    private BigDecimal alipay;//支付宝
    private BigDecimal onlinePay;//线上支付
    private BigDecimal creditSum;//贷方总记
    private Date nightAuditorTime;//夜审时间
    private Integer hotelId;//酒店id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(BigDecimal roomRate) {
        this.roomRate = roomRate;
    }

    public BigDecimal getTimeoutRoomRate() {
        return timeoutRoomRate;
    }

    public void setTimeoutRoomRate(BigDecimal timeoutRoomRate) {
        this.timeoutRoomRate = timeoutRoomRate;
    }

    public BigDecimal getRoomRateAdjustment() {
        return roomRateAdjustment;
    }

    public void setRoomRateAdjustment(BigDecimal roomRateAdjustment) {
        this.roomRateAdjustment = roomRateAdjustment;
    }

    public BigDecimal getOtherRate() {
        return otherRate;
    }

    public void setOtherRate(BigDecimal otherRate) {
        this.otherRate = otherRate;
    }

    public BigDecimal getCommodity() {
        return commodity;
    }

    public void setCommodity(BigDecimal commodity) {
        this.commodity = commodity;
    }

    public BigDecimal getCompensation() {
        return compensation;
    }

    public void setCompensation(BigDecimal compensation) {
        this.compensation = compensation;
    }

    public BigDecimal getMemberCardRate() {
        return memberCardRate;
    }

    public void setMemberCardRate(BigDecimal memberCardRate) {
        this.memberCardRate = memberCardRate;
    }

    public BigDecimal getDebtSum() {
        return debtSum;
    }

    public void setDebtSum(BigDecimal debtSum) {
        this.debtSum = debtSum;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getBankCard() {
        return bankCard;
    }

    public void setBankCard(BigDecimal bankCard) {
        this.bankCard = bankCard;
    }

    public BigDecimal getWechat() {
        return wechat;
    }

    public void setWechat(BigDecimal wechat) {
        this.wechat = wechat;
    }

    public BigDecimal getAlipay() {
        return alipay;
    }

    public void setAlipay(BigDecimal alipay) {
        this.alipay = alipay;
    }

    public BigDecimal getOnlinePay() {
        return onlinePay;
    }

    public void setOnlinePay(BigDecimal onlinePay) {
        this.onlinePay = onlinePay;
    }

    public BigDecimal getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(BigDecimal creditSum) {
        this.creditSum = creditSum;
    }

    public Date getNightAuditorTime() {
        return nightAuditorTime;
    }

    public void setNightAuditorTime(Date nightAuditorTime) {
        this.nightAuditorTime = nightAuditorTime;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
}

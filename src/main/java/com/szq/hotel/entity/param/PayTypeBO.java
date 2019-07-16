package com.szq.hotel.entity.param;

import java.math.BigDecimal;

public class PayTypeBO {

    private BigDecimal cash;//现金
    private BigDecimal cart;//银行卡
    private BigDecimal wechat;//微信
    private BigDecimal alipay;//支付宝
    private BigDecimal other;//其他支付
    private BigDecimal stored;//储值
    private BigDecimal money;//金额 ?收款
    private String certificateNumber;//证件号
    private BigDecimal integral;//积分 减免


    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getCart() {
        return cart;
    }

    public void setCart(BigDecimal cart) {
        this.cart = cart;
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

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getStored() {
        return stored;
    }

    public void setStored(BigDecimal stored) {
        this.stored = stored;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }
}

package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class OrderListBO extends BaseModel {

    private Integer id;//订单表
    private Integer orderId;
    private Date checkTime;//入住时间
    private Date checkOutTime;//离店时间
    private String roomName;//房号
    private String roomType;//房型
    private String orderPlacer;//姓名
    private String phone;//手机号
    private String channel;//渠道
    private String OTA;//第三方订单号
    private String orderType;//客源
    private String checkType;//入住方式
    private BigDecimal unitPrice;//首日单价
    private String orderState;//订单状态
    private String certificateNumber;//证件号


    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getOrderPlacer() {
        return orderPlacer;
    }

    public void setOrderPlacer(String orderPlacer) {
        this.orderPlacer = orderPlacer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOTA() {
        return OTA;
    }

    public void setOTA(String OTA) {
        this.OTA = OTA;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    @Override
    public String toString() {
        return "OrderListBO{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", checkTime=" + checkTime +
                ", checkOutTime=" + checkOutTime +
                ", roomName='" + roomName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", orderPlacer='" + orderPlacer + '\'' +
                ", phone='" + phone + '\'' +
                ", channel='" + channel + '\'' +
                ", OTA='" + OTA + '\'' +
                ", orderType='" + orderType + '\'' +
                ", checkType='" + checkType + '\'' +
                ", unitPrice=" + unitPrice +
                ", orderState='" + orderState + '\'' +
                '}';
    }
}
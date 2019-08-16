package com.szq.hotel.entity.bo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OrderChild {
    private Integer id;

    private Integer orderId;

    private String startTime;

    private String endTime;

    private Double payCashNum;

    private Double otherPayNum;

    private Double roomRate;

    private Double otherRate;

    private Double timeoutRate;

    private Double freeRateNum;

    private String orderState;

    private Date practicalDepartureTime;

    private String remark;

    private Integer roomId;

    private Integer roomTypeId;

    private String alRoomCode;

    private String nightAuditorState;

    private String printState;

    private String main;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getPayCashNum() {
        return payCashNum;
    }

    public void setPayCashNum(Double payCashNum) {
        this.payCashNum = payCashNum;
    }

    public Double getOtherPayNum() {
        return otherPayNum;
    }

    public void setOtherPayNum(Double otherPayNum) {
        this.otherPayNum = otherPayNum;
    }

    public Double getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(Double roomRate) {
        this.roomRate = roomRate;
    }

    public Double getOtherRate() {
        return otherRate;
    }

    public void setOtherRate(Double otherRate) {
        this.otherRate = otherRate;
    }

    public Double getTimeoutRate() {
        return timeoutRate;
    }

    public void setTimeoutRate(Double timeoutRate) {
        this.timeoutRate = timeoutRate;
    }

    public Double getFreeRateNum() {
        return freeRateNum;
    }

    public void setFreeRateNum(Double freeRateNum) {
        this.freeRateNum = freeRateNum;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState == null ? null : orderState.trim();
    }

    public Date getPracticalDepartureTime() {
        return practicalDepartureTime;
    }

    public void setPracticalDepartureTime(Date practicalDepartureTime) {
        this.practicalDepartureTime = practicalDepartureTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getAlRoomCode() {
        return alRoomCode;
    }

    public void setAlRoomCode(String alRoomCode) {
        this.alRoomCode = alRoomCode == null ? null : alRoomCode.trim();
    }

    public String getNightAuditorState() {
        return nightAuditorState;
    }

    public void setNightAuditorState(String nightAuditorState) {
        this.nightAuditorState = nightAuditorState == null ? null : nightAuditorState.trim();
    }

    public String getPrintState() {
        return printState;
    }

    public void setPrintState(String printState) {
        this.printState = printState == null ? null : printState.trim();
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main == null ? null : main.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OrderChild{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", payCashNum=" + payCashNum +
                ", otherPayNum=" + otherPayNum +
                ", roomRate=" + roomRate +
                ", otherRate=" + otherRate +
                ", timeoutRate=" + timeoutRate +
                ", freeRateNum=" + freeRateNum +
                ", orderState='" + orderState + '\'' +
                ", practicalDepartureTime=" + practicalDepartureTime +
                ", remark='" + remark + '\'' +
                ", roomId=" + roomId +
                ", roomTypeId=" + roomTypeId +
                ", alRoomCode='" + alRoomCode + '\'' +
                ", nightAuditorState='" + nightAuditorState + '\'' +
                ", printState='" + printState + '\'' +
                ", main='" + main + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
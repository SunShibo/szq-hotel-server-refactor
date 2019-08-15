package com.szq.hotel.entity.param;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

//备份退房信息  用于退房回滚
public class OrderChildBackupParam {
    private Integer id;//子订单id
    private BigDecimal roomRate=new BigDecimal(0);//房费 这天的房费
    private Integer roomRateRecordId;//房费 这天的房费
    private BigDecimal otherRate=new BigDecimal(0);//其他费用 超时费存这就行
    private Integer otherRateRecordId;//其他费用 超时费存这就行
    private BigDecimal timeoutRate=new BigDecimal(0);//超时费减免
    private Integer timeoutRateRecordId;
    private String orderState;//订单状态
    private String roomMajorState;//房态
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//预离店时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date practicalDepartureTime;//实际离店时间
    private Integer nightAuditId;//人晚数id

    public Integer getNightAuditId() {
        return nightAuditId;
    }

    public void setNightAuditId(Integer nightAuditId) {
        this.nightAuditId = nightAuditId;
    }

    public Integer getTimeoutRateRecordId() {
        return timeoutRateRecordId;
    }

    public void setTimeoutRateRecordId(Integer timeoutRateRecordId) {
        this.timeoutRateRecordId = timeoutRateRecordId;
    }

    public BigDecimal getTimeoutRate() {
        return timeoutRate;
    }

    public void setTimeoutRate(BigDecimal timeoutRate) {
        this.timeoutRate = timeoutRate;
    }

    public Integer getRoomRateRecordId() {
        return roomRateRecordId;
    }

    public void setRoomRateRecordId(Integer roomRateRecordId) {
        this.roomRateRecordId = roomRateRecordId;
    }

    public Integer getOtherRateRecordId() {
        return otherRateRecordId;
    }

    public void setOtherRateRecordId(Integer otherRateRecordId) {
        this.otherRateRecordId = otherRateRecordId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getPracticalDepartureTime() {
        return practicalDepartureTime;
    }

    public void setPracticalDepartureTime(Date practicalDepartureTime) {
        this.practicalDepartureTime = practicalDepartureTime;
    }

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

    public BigDecimal getOtherRate() {
        return otherRate;
    }

    public void setOtherRate(BigDecimal otherRate) {
        this.otherRate = otherRate;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getRoomMajorState() {
        return roomMajorState;
    }

    public void setRoomMajorState(String roomMajorState) {
        this.roomMajorState = roomMajorState;
    }
}

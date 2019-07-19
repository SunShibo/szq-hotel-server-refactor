package com.szq.hotel.entity.param;

import java.math.BigDecimal;
import java.util.Date;

//备份退房信息  用于退房回滚
public class OrderChildBackupParam {
    private Integer id;//子订单id
    private BigDecimal roomRate;//房费 这天的房费
    private BigDecimal otherRate;//其他费用 超时费存这就行
    private String orderState;//订单状态
    private String roomMajorState;//房态
    private Date endTime;//预离店时间
    private Date practicalDepartureTime;//实际离店时间

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

package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.util.Date;

public class PrintBO extends BaseModel{
    private Integer id;
    private String orderNumber;
    private String roomName;
    private String name;
    private Date startTime;
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PrintBO{" +
                "orderNumber='" + orderNumber + '\'' +
                ", roomName='" + roomName + '\'' +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}

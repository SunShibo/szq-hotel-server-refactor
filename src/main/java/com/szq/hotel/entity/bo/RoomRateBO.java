package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.util.Date;

public class RoomRateBO extends BaseModel {
    private Integer id;//子订单
    private Date startTime;//入住时间
    private Date endTime;//退房时间
    private String orderState;//订单状态
    private String roomName;//房间号
    private String roomTypeName;//房型名字
    private String alRoomCode;//联房识别码
    private String channel;//渠道
    private String passengerSource;//客源
    private String OTA;
    private String orderNumber;//订单
    private Integer hotelId;//酒店id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
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

    public String getAlRoomCode() {
        return alRoomCode;
    }

    public void setAlRoomCode(String alRoomCode) {
        this.alRoomCode = alRoomCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPassengerSource() {
        return passengerSource;
    }

    public void setPassengerSource(String passengerSource) {
        this.passengerSource = passengerSource;
    }

    public String getOTA() {
        return OTA;
    }

    public void setOTA(String OTA) {
        this.OTA = OTA;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "RoomRateBO{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderState='" + orderState + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", alRoomCode='" + alRoomCode + '\'' +
                ", channel='" + channel + '\'' +
                ", passengerSource='" + passengerSource + '\'' +
                ", OTA='" + OTA + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", hotelId=" + hotelId +
                '}';
    }
}

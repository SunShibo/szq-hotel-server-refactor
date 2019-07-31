package com.szq.hotel.entity.param;

import java.util.Date;

/**
 * 订单查询条件
 */
public class OrderParam {

    private Integer hotelId;//酒店id
    private String orderState;//订单状态
    private String admissionsStartTime;//入住开始时间
    private String admissionsEndTime;//入住结束时间
    private String departureStartTime;//离店开始时间
    private String departureEndTime;//离店结束时间
    private String orderType;//入住方式
    private String checkType;//入住类型
    private String orderPlacer;//下单人名字
    private String phone;//电话号
    private String roomName;//房间号
    private String channel;//渠道
    private String OTA;//第三方订单号
    private Integer roomTypeId;//房型id
    private Integer pageNo;
    private Integer pageSize;
    private Integer pageOffset;
    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }


    public String getAdmissionsStartTime() {
        return admissionsStartTime;
    }

    public void setAdmissionsStartTime(String admissionsStartTime) {
        this.admissionsStartTime = admissionsStartTime;
    }

    public String getAdmissionsEndTime() {
        return admissionsEndTime;
    }

    public void setAdmissionsEndTime(String admissionsEndTime) {
        this.admissionsEndTime = admissionsEndTime;
    }

    public String getDepartureStartTime() {
        return departureStartTime;
    }

    public void setDepartureStartTime(String departureStartTime) {
        this.departureStartTime = departureStartTime;
    }

    public String getDepartureEndTime() {
        return departureEndTime;
    }

    public void setDepartureEndTime(String departureEndTime) {
        this.departureEndTime = departureEndTime;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(Integer pageOffset) {
        this.pageOffset = pageOffset;
    }



    @Override
    public String toString() {
        return "OrderParam{" +
                "hotelId=" + hotelId +
                ", orderState='" + orderState + '\'' +
                ", admissionsStartTime=" + admissionsStartTime +
                ", admissionsEndTime=" + admissionsEndTime +
                ", departureStartTime=" + departureStartTime +
                ", departureEndTime=" + departureEndTime +
                ", orderType='" + orderType + '\'' +
                ", checkType='" + checkType + '\'' +
                ", orderPlacer='" + orderPlacer + '\'' +
                ", phone='" + phone + '\'' +
                ", roomName='" + roomName + '\'' +
                ", channel='" + channel + '\'' +
                ", OTA='" + OTA + '\'' +
                ", roomTypeId=" + roomTypeId +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
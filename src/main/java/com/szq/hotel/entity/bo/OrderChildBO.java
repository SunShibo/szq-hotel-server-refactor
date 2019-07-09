package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderChildBO extends BaseModel {
    private Integer id;//子订单
    private Integer orderId;//订单id
    private Date startTime;//入住时间
    private Date endTime;//退房时间
    private Integer certificateTypeId;//证件类型id
    private BigDecimal payCashNum;//支付现金 金额
    private BigDecimal otherPayNum;//其他支付金额
    private BigDecimal roomRate;//房费 选定时定的价格，有可能改价
    private BigDecimal otherRate;//其他费用
    private BigDecimal timeoutRate;//超时费用
    private BigDecimal freeRateNum;//免单金额
    private String orderState;//订单状态
    private Date practicalDepartureTime;//实际退房时间
    private Integer roomId;//房间id
    private String roomName;//房间号
    private Integer roomTypeId;//房型id
    private String roomTypeName;//房型名字
    private String alRoomCode;//联房识别码
    private String nightAuditorState;//夜审状态
    private String printState;//打印状态（备用字段）
    private String remark;//备注
    private List<EverydayRoomPriceBO> everydayRoomPriceBOS;//每日房价信息
    private List<CheckInPersonBO> checkInPersonBOS;//入住人员信息

    public List<CheckInPersonBO> getCheckInPersonBOS() {
        return checkInPersonBOS;
    }

    public void setCheckInPersonBOS(List<CheckInPersonBO> checkInPersonBOS) {
        this.checkInPersonBOS = checkInPersonBOS;
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



    public List<EverydayRoomPriceBO> getEverydayRoomPriceBOS() {
        return everydayRoomPriceBOS;
    }

    public void setEverydayRoomPriceBOS(List<EverydayRoomPriceBO> everydayRoomPriceBOS) {
        this.everydayRoomPriceBOS = everydayRoomPriceBOS;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public Integer getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(Integer certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public BigDecimal getPayCashNum() {
        return payCashNum;
    }

    public void setPayCashNum(BigDecimal payCashNum) {
        this.payCashNum = payCashNum;
    }

    public BigDecimal getOtherPayNum() {
        return otherPayNum;
    }

    public void setOtherPayNum(BigDecimal otherPayNum) {
        this.otherPayNum = otherPayNum;
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

    public BigDecimal getTimeoutRate() {
        return timeoutRate;
    }

    public void setTimeoutRate(BigDecimal timeoutRate) {
        this.timeoutRate = timeoutRate;
    }

    public BigDecimal getFreeRateNum() {
        return freeRateNum;
    }

    public void setFreeRateNum(BigDecimal freeRateNum) {
        this.freeRateNum = freeRateNum;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public Date getPracticalDepartureTime() {
        return practicalDepartureTime;
    }

    public void setPracticalDepartureTime(Date practicalDepartureTime) {
        this.practicalDepartureTime = practicalDepartureTime;
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
        this.alRoomCode = alRoomCode;
    }

    public String getNightAuditorState() {
        return nightAuditorState;
    }

    public void setNightAuditorState(String nightAuditorState) {
        this.nightAuditorState = nightAuditorState;
    }

    public String getPrintState() {
        return printState;
    }

    public void setPrintState(String printState) {
        this.printState = printState;
    }
}

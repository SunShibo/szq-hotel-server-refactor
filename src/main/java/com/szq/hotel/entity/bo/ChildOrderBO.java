package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class ChildOrderBO extends BaseModel {
    private Integer id;//子订单
    private Date startTime;//入住时间
    private Date endTime;//退房时间
    private BigDecimal payCashNum;//支付现金 金额
    private BigDecimal otherPayNum;//其他支付金额
    private BigDecimal roomRate;//房费 //未查询!!!!!!!
    private BigDecimal otherRate;//其他费用
    private BigDecimal timeoutRate;//超时费用
    private BigDecimal freeRateNum;//免单金额
    private String orderState;//订单状态
    private String roomName;//房间号
    private String roomTypeName;//房型名字
    private String alRoomCode;//联房识别码
    private String nightAuditorState;//夜审状态
    private String printState;//打印状态（备用字段）
    private String remark;//备注
    private String name;
    private String channel;//渠道
    private String passengerSource;//客源
    private String OTA;
    private String orderNumber;//订单
    private String main;//主账房
    private Integer membersId;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Integer getMembersId() {
        return membersId;
    }

    public void setMembersId(Integer membersId) {
        this.membersId = membersId;
    }
}

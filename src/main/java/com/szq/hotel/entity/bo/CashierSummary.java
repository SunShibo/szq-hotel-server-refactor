package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;


public class CashierSummary extends BaseModel {
    private Integer id;
    private String project;//项目
    private String type;//支付类型
    private String channel;//渠道
    private String passengerSource;//客源
    private String OTA;//OTA
    private String roomName;//房间名字
    private String roomTypeName;//房间类型
    private String name;//姓名
    private double consumption;
    private double settlement;
    private Date createTime;//发生日期
    private String remark;//备注
    private Integer userId;//操作员
    private String orderNumber;//订单号
    private Date startTime;//查询条件
    private Date endTime;//
    private String operator;
    private Integer hotelId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getSettlement() {
        return settlement;
    }

    public void setSettlement(double settlement) {
        this.settlement = settlement;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
}

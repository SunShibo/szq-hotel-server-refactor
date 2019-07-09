package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import javax.xml.crypto.Data;
import java.security.DigestOutputStream;
import java.security.interfaces.DSAPublicKey;
import java.util.Date;

/**
 * @Author: Bin Wang
 * @date: Created in 16:43 2019/7/9
 */
public class OcBO extends BaseModel {
    private Integer id;//id
    private Integer orderId;//主订单id
    private Date  startTime;//入住时间
    private Date  endTime;//离开时间
    private Double payCashNum;//支付现金金额
    private Double otherPayNum;//其他支付金额
    private Double roomRate;//房费
    private Double otherRate;//其他费用
    private Double timeoutRate;//超时费用
    private Double freeRateNum;//免单金额
    private String orderState;//订单状态
    private Date practicalDepartureTime;//实际离店时间
    private String remark;//备注
    private Integer roomId;//房间id
    private Integer roomTypeId;//房型id
    private String alRoomCode;//联房识别码
    private String nightAuditorState;//夜申状态
    private String printState;//打印状态(备用字段)

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
        this.orderState = orderState;
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
        this.remark = remark;
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

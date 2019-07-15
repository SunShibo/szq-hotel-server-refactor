package com.szq.hotel.entity.result;

import com.szq.hotel.entity.bo.CheckInPersonBO;

import java.util.List;

//首页在住信息
public class CheckInInfoResult {
    private Integer orderId;//主订单id
    private Integer orderChildId;//子订单id
    private String startTime;//入住时间
    private String endTime;//离店时间
    private String totalConsumption;//花费金额
    private String sumPayment;//支付金额
    private String alRoomCode;//联房码
    private String channel;//客源
    private String remake;//备注
    private String OTA;//
    private String money;//当天房价

    public String getOTA() {
        return OTA;
    }

    public void setOTA(String OTA) {
        this.OTA = OTA;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    private List<CheckRoomPersonResult> checkRoomPersonResults;//联房信息
    private List<CheckInPersonBO> checkInPersonBOS;//同住人

    public List<CheckRoomPersonResult> getCheckRoomPersonResults() {
        return checkRoomPersonResults;
    }

    public void setCheckRoomPersonResults(List<CheckRoomPersonResult> checkRoomPersonResults) {
        this.checkRoomPersonResults = checkRoomPersonResults;
    }

    public List<CheckInPersonBO> getCheckInPersonBOS() {
        return checkInPersonBOS;
    }

    public void setCheckInPersonBOS(List<CheckInPersonBO> checkInPersonBOS) {
        this.checkInPersonBOS = checkInPersonBOS;
    }

    public String getAlRoomCode() {
        return alRoomCode;
    }

    public void setAlRoomCode(String alRoomCode) {
        this.alRoomCode = alRoomCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderChildId() {
        return orderChildId;
    }

    public void setOrderChildId(Integer orderChildId) {
        this.orderChildId = orderChildId;
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

    public String getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(String totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public String getSumPayment() {
        return sumPayment;
    }

    public void setSumPayment(String sumPayment) {
        this.sumPayment = sumPayment;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }
}

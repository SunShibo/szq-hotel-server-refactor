package com.szq.hotel.entity.result;

import com.szq.hotel.entity.bo.CheckInPersonBO;

import java.util.Date;
import java.util.List;

//首页在住信息
public class CheckInInfoResult {
    private String orderPlacer;//下单人名称
    private String phone;//预约手机号
    private String subscribeRemark;//预约备注
    private String orderType;//订单类型
    private Integer orderId;//主订单id
    private Integer orderChildId;//子订单id
    private Date startTime;//入住时间
    private Date endTime;//离店时间
    private Date practicalDepartureTime;//实际离店时间
    private String totalConsumption;//花费金额
    private String sumPayment;//支付金额
    private String alRoomCode;//联房码
    private String channel;//客源
    private String remake;//备注
    private String OTA;//
    private String money;//当天房价

    public Date getPracticalDepartureTime() {
        return practicalDepartureTime;
    }

    public void setPracticalDepartureTime(Date practicalDepartureTime) {
        this.practicalDepartureTime = practicalDepartureTime;
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

    public String getSubscribeRemark() {
        return subscribeRemark;
    }

    public void setSubscribeRemark(String subscribeRemark) {
        this.subscribeRemark = subscribeRemark;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

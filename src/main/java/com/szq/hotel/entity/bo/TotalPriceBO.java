package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Bin Wang
 * @date: Created in 11:08 2019/7/30
 */
public class TotalPriceBO extends BaseModel {

    private Integer id;//订单表
    private String orderPlacer;//下单人名称
    private String orderNumber;//单号
    private String checkType;//入住方式
    private String phone;//手机号
    private Date checkTime;//入住时间
    private Date checkOutTime;//离店时间
    private Integer dayCount;//入住天数
    private Integer IDNumberType;//证件类型
    private String IDNumber;//证件号码
    private String orderType;//订单类型（subscribe预约入住,directly直接入住）
    private String clerkOrderingName;//接单员姓名
    private Integer clerkOrderingId;//接单员id
    private Integer hotelId;//酒店id
    private Integer membersId;//会员id
    private Integer memberIdOrOrganizationId;//会员id 合作机构id
    private String subscribeRemark;//预约备注
    private BigDecimal totalPrice;
    private Date createTime;
    private Date updateTime;
    private String OTA;
    private String channel;//客源

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderPlacer() {
        return orderPlacer;
    }

    public void setOrderPlacer(String orderPlacer) {
        this.orderPlacer = orderPlacer;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getIDNumberType() {
        return IDNumberType;
    }

    public void setIDNumberType(Integer IDNumberType) {
        this.IDNumberType = IDNumberType;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getClerkOrderingName() {
        return clerkOrderingName;
    }

    public void setClerkOrderingName(String clerkOrderingName) {
        this.clerkOrderingName = clerkOrderingName;
    }

    public Integer getClerkOrderingId() {
        return clerkOrderingId;
    }

    public void setClerkOrderingId(Integer clerkOrderingId) {
        this.clerkOrderingId = clerkOrderingId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getMembersId() {
        return membersId;
    }

    public void setMembersId(Integer membersId) {
        this.membersId = membersId;
    }

    public Integer getMemberIdOrOrganizationId() {
        return memberIdOrOrganizationId;
    }

    public void setMemberIdOrOrganizationId(Integer memberIdOrOrganizationId) {
        this.memberIdOrOrganizationId = memberIdOrOrganizationId;
    }

    public String getSubscribeRemark() {
        return subscribeRemark;
    }

    public void setSubscribeRemark(String subscribeRemark) {
        this.subscribeRemark = subscribeRemark;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOTA() {
        return OTA;
    }

    public void setOTA(String OTA) {
        this.OTA = OTA;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

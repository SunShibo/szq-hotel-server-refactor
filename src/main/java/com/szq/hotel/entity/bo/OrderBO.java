package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderBO extends BaseModel {
    private Integer id;//订单表
    private String orderPlacer;//下单人名称
    private String orderNumber;//单号
    private String checkType;//入住方式
    private String phone;//手机号
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date checkTime;//入住时间
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
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
    private BigDecimal totalPrice=new BigDecimal(0);//总房价
    private Date createTime;
    private Date updateTime;
    private String OTA;
    private String channel;//客源

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

    private List<OrderChildBO> orderChildBOS;

    public List<OrderChildBO> getOrderChildBOS() {
        return orderChildBOS;
    }

    public void setOrderChildBOS(List<OrderChildBO> orderChildBOS) {
        this.orderChildBOS = orderChildBOS;
    }

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

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getMemberIdOrOrganizationId() {
        return memberIdOrOrganizationId;
    }

    public void setMemberIdOrOrganizationId(Integer memberIdOrOrganizationId) {
        this.memberIdOrOrganizationId = memberIdOrOrganizationId;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
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

    public Integer getMembersId() {
        return membersId;
    }

    public void setMembersId(Integer membersId) {
        this.membersId = membersId;
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
}

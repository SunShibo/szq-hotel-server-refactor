package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Bin Wang
 * @date: Created in 14:55 2019/7/24
 */
public class CommodityTransactionBO extends BaseModel {
    private int id;
    private int memberId;
    private String orderNumber;
    private Date createTime;
    private String payType;
    private String consumeType;
    private BigDecimal money;
    private int createUserId;
    private String consumptionDetails;
    private int hotelId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getConsumptionDetails() {
        return consumptionDetails;
    }

    public void setConsumptionDetails(String consumptionDetails) {
        this.consumptionDetails = consumptionDetails;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "CommodityTransactionBO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", orderNumber='" + orderNumber + '\'' +
                ", createTime=" + createTime +
                ", payType='" + payType + '\'' +
                ", consumeType='" + consumeType + '\'' +
                ", money=" + money +
                ", createUserId=" + createUserId +
                ", consumptionDetails='" + consumptionDetails + '\'' +
                ", hotelId=" + hotelId +
                '}';
    }
}

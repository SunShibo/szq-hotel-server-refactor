package com.szq.hotel.entity.bo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品交易
 */
public class CommodityBO {
    private Integer id;
    private String orderNumber;//订单号
    private Date createTime;//创建时间
    private String payType;//支付方式
    private String consumeType;//消费方式
    private BigDecimal money;//金额
    private Integer createUserId;//操作员id
    private String consumptionDetails;//交易详情
    private Integer hotelId;//酒店id
    private String name;//操作员名字
    public CommodityBO() {}


    public CommodityBO( String orderNumber, String payType, String consumeType, BigDecimal money, Integer createUserId, String consumptionDetails,Integer hotelId) {
        this.orderNumber = orderNumber;
        this.payType = payType;
        this.consumeType = consumeType;
        this.money = money;
        this.createUserId = createUserId;
        this.consumptionDetails = consumptionDetails;
        this.hotelId=hotelId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getConsumptionDetails() {
        return consumptionDetails;
    }

    public void setConsumptionDetails(String consumptionDetails) {
        this.consumptionDetails = consumptionDetails;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CommodityBO{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", createTime=" + createTime +
                ", payType='" + payType + '\'' +
                ", consumeType='" + consumeType + '\'' +
                ", money=" + money +
                ", createUserId=" + createUserId +
                ", consumptionDetails='" + consumptionDetails + '\'' +
                ", hotelId=" + hotelId +
                ", name=" + name +
                '}';
    }
}

package com.szq.hotel.entity.bo;


import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class OrderRecoredBO extends BaseModel {
    private Integer id;
    private Integer orderChildId;//子订单id
    private String info;//详细信息
    private Integer createUserId;//创建用户id
    private String payType;//支付方式
    private BigDecimal money;//金额
    private String state;//状态
    private String project;//项目
    private String number;//数量
    private Date createTime;//创建时间


    public OrderRecoredBO() {
    }

    public OrderRecoredBO(Integer orderChildId, String info, Integer createUserId, String payType, BigDecimal money, String project, String number) {
        this.orderChildId = orderChildId;
        this.info = info;
        this.createUserId = createUserId;
        this.payType = payType;
        this.money = money;
        this.project = project;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderChildId() {
        return orderChildId;
    }

    public void setOrderChildId(Integer orderChildId) {
        this.orderChildId = orderChildId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "OrderRecoredBO{" +
                "id=" + id +
                ", orderChildId=" + orderChildId +
                ", info='" + info + '\'' +
                ", createUserId=" + createUserId +
                ", payType='" + payType + '\'' +
                ", money=" + money +
                ", state='" + state + '\'' +
                ", project='" + project + '\'' +
                ", number='" + number + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

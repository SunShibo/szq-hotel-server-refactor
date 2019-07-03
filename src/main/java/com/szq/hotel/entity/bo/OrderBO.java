package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.util.Date;

public class OrderBO extends BaseModel {
    private Integer id;//订单表
    private String orderPlacer;//下单人名称
    private Integer orderNumber;//单号
    private String phone;//手机号
    private String IDNumber;//证件号码
    private String orderType;//订单类型
    private String remark;//备注
    private String memberOrOrganization;//会员合作机构
    private Integer memberIdOrOrganizationId;//会员id 合作机构id
    private Date createTime;
    private Date updateTime;

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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMemberOrOrganization() {
        return memberOrOrganization;
    }

    public void setMemberOrOrganization(String memberOrOrganization) {
        this.memberOrOrganization = memberOrOrganization;
    }

    public Integer getMemberIdOrOrganizationId() {
        return memberIdOrOrganizationId;
    }

    public void setMemberIdOrOrganizationId(Integer memberIdOrOrganizationId) {
        this.memberIdOrOrganizationId = memberIdOrOrganizationId;
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

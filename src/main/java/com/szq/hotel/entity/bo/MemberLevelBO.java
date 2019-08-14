package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class MemberLevelBO extends BaseModel {
    private Integer id;//会员表
    private String name;//会员级别名称
    private String state;//状态（yes/no）
    private String type;//能否储值（yes/no）
    private BigDecimal consumeGetIntegral;//消费1元获得多少积分
    private BigDecimal integralToMoney;//1积分抵多少元
    private BigDecimal discount;//折扣
    private Integer createUserId;//创建人id
    private Date createTime;//创建时间
    private Integer updateUserId;//修改人id
    private Date updateTime;//修改时间

    public BigDecimal getIntegralToMoney() {
        return integralToMoney;
    }

    public void setIntegralToMoney(BigDecimal integralToMoney) {
        this.integralToMoney = integralToMoney;
    }

    public BigDecimal getConsumeGetIntegral() {
        return consumeGetIntegral;
    }

    public void setConsumeGetIntegral(BigDecimal consumeGetIntegral) {
        this.consumeGetIntegral = consumeGetIntegral;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

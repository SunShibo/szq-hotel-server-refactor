package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class StoredValueRecordBO extends BaseModel {
    private Integer id; //储值记录表
    private Integer checkInMemberId; //入住会员id
    private String type; //类型
    private String oddNumbers; //单号
    private BigDecimal storedValueMoney; //储值金额
    private BigDecimal presenterMoney; //赠送金额
    private BigDecimal currentBalance; //当前余额
    private Integer createUserId; //操作人id
    private Date createTime; //创建时间
    private String remark; //备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckInMemberId() {
        return checkInMemberId;
    }

    public void setCheckInMemberId(Integer checkInMemberId) {
        this.checkInMemberId = checkInMemberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOddNumbers() {
        return oddNumbers;
    }

    public void setOddNumbers(String oddNumbers) {
        this.oddNumbers = oddNumbers;
    }

    public BigDecimal getStoredValueMoney() {
        return storedValueMoney;
    }

    public void setStoredValueMoney(BigDecimal storedValueMoney) {
        this.storedValueMoney = storedValueMoney;
    }

    public BigDecimal getPresenterMoney() {
        return presenterMoney;
    }

    public void setPresenterMoney(BigDecimal presenterMoney) {
        this.presenterMoney = presenterMoney;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

public class MemberResultBO extends BaseModel{
    private String memberName;//会员名称
    private String cardNumber;//卡号
    private String memberLevelName;//会员级别名称
    private double integral;//积分

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMemberLevelName() {
        return memberLevelName;
    }

    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }
}

package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class ShiftRecordsBO extends BaseModel {

    private Integer id;
    private Integer classesId;//班次id
    private Integer adminId;//操作员id
    private BigDecimal generalIncome;//总收入
    private BigDecimal cashIncome;//现金收
    private BigDecimal cashBack;//现金退
    private BigDecimal cashAmount;//现金合计
    private BigDecimal bankCardIncome;//银行卡收
    private BigDecimal bankCardBack;//银行卡退
    private BigDecimal bankCardAmount;//银行卡合计
    private BigDecimal wechatIncome;//微信收
    private BigDecimal wechatBack;//微信退
    private BigDecimal wechatAmount;//微信合计
    private BigDecimal alipayIncome;//支付宝收
    private BigDecimal alipayBack;//支付宝退
    private BigDecimal alipayAmount;//支付宝合计
    private BigDecimal integralIncome;//积分进
    private BigDecimal integralSpend;//积分出
    private BigDecimal integralAmount;//积分合计
    private BigDecimal storedValueIncome;//储值收
    private BigDecimal storedValueBack;//储值退
    private BigDecimal storedValueAmount;//储值合计
    private BigDecimal memberCardSellCount;//会员卡售出数量
    private Date attendanceTime;//上班时间
    private Date closingTime;//下班时间
    private String name;//操作员名字
    private String startTime;//班次开始时间
    private String endTime;//班次结束时间
    private String classesName;//班次名称

    public ShiftRecordsBO() { }

    public ShiftRecordsBO(Integer classesId, Integer adminId) {
        this.classesId = classesId;
        this.adminId = adminId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public BigDecimal getGeneralIncome() {
        if(generalIncome!=null)
             return generalIncome;
        else
            return new BigDecimal("0");
    }

    public void setGeneralIncome(BigDecimal generalIncome) {
        this.generalIncome = generalIncome;
    }

    public BigDecimal getCashIncome() {
        if(cashIncome!=null)
            return cashIncome;
        else
            return new BigDecimal("0");
    }

    public void setCashIncome(BigDecimal cashIncome) {
        this.cashIncome = cashIncome;
    }

    public BigDecimal getCashBack() {
        if(cashBack!=null)
            return cashBack;
        else
            return new BigDecimal("0");
    }

    public void setCashBack(BigDecimal cashBack) {
        this.cashBack = cashBack;
    }

    public BigDecimal getCashAmount() {
        if(cashAmount!=null)
            return cashAmount;
        else
            return new BigDecimal("0");
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getBankCardIncome() {
        if(bankCardIncome!=null)
            return bankCardIncome;
        else
            return new BigDecimal("0");
    }

    public void setBankCardIncome(BigDecimal bankCardIncome) {
        this.bankCardIncome = bankCardIncome;
    }

    public BigDecimal getBankCardBack() {
        if(bankCardBack!=null)
            return bankCardBack;
        else
            return new BigDecimal("0");
    }

    public void setBankCardBack(BigDecimal bankCardBack) {
        this.bankCardBack = bankCardBack;
    }

    public BigDecimal getBankCardAmount() {
        if(bankCardAmount!=null)
            return bankCardAmount;
        else
            return new BigDecimal("0");
    }

    public void setBankCardAmount(BigDecimal bankCardAmount) {
        this.bankCardAmount = bankCardAmount;
    }

    public BigDecimal getWechatIncome() {
        if(wechatIncome!=null)
            return wechatIncome;
        else
            return new BigDecimal("0");
    }

    public void setWechatIncome(BigDecimal wechatIncome) {
        this.wechatIncome = wechatIncome;
    }

    public BigDecimal getWechatBack() {
        if(wechatBack!=null)
            return wechatBack;
        else
            return new BigDecimal("0");
    }

    public void setWechatBack(BigDecimal wechatBack) {
        this.wechatBack = wechatBack;
    }

    public BigDecimal getWechatAmount() {
        if(wechatAmount!=null)
            return wechatAmount;
        else
            return new BigDecimal("0");
    }

    public void setWechatAmount(BigDecimal wechatAmount) {
        this.wechatAmount = wechatAmount;
    }

    public BigDecimal getAlipayIncome() {
        if(alipayIncome!=null)
            return alipayIncome;
        else
            return new BigDecimal("0");
    }

    public void setAlipayIncome(BigDecimal alipayIncome) {
        this.alipayIncome = alipayIncome;
    }

    public BigDecimal getAlipayBack() {
        if(alipayBack!=null)
            return alipayBack;
        else
            return new BigDecimal("0");
    }

    public void setAlipayBack(BigDecimal alipayBack) {
        this.alipayBack = alipayBack;
    }

    public BigDecimal getAlipayAmount() {
        if(alipayAmount!=null)
            return alipayAmount;
        else
            return new BigDecimal("0");
    }

    public void setAlipayAmount(BigDecimal alipayAmount) {
        this.alipayAmount = alipayAmount;
    }

    public BigDecimal getIntegralIncome() {
        if(integralIncome!=null)
            return integralIncome;
        else
            return new BigDecimal("0");
    }

    public void setIntegralIncome(BigDecimal integralIncome) {
        this.integralIncome = integralIncome;
    }

    public BigDecimal getIntegralSpend() {
        if(integralSpend!=null)
            return integralSpend;
        else
            return new BigDecimal("0");
    }

    public void setIntegralSpend(BigDecimal integralSpend) {
        this.integralSpend = integralSpend;
    }

    public BigDecimal getIntegralAmount() {
        if(integralAmount!=null)
            return integralAmount;
        else
            return new BigDecimal("0");
    }

    public void setIntegralAmount(BigDecimal integralAmount) {
        this.integralAmount = integralAmount;
    }

    public BigDecimal getStoredValueIncome() {
        if(storedValueIncome!=null)
            return storedValueIncome;
        else
            return new BigDecimal("0");
    }

    public void setStoredValueIncome(BigDecimal storedValueIncome) {
        this.storedValueIncome = storedValueIncome;
    }

    public BigDecimal getStoredValueBack() {
        if(storedValueBack!=null)
            return storedValueBack;
        else
            return new BigDecimal("0");
    }

    public void setStoredValueBack(BigDecimal storedValueBack) {
        this.storedValueBack = storedValueBack;
    }

    public BigDecimal getStoredValueAmount() {
        if(storedValueAmount!=null)
            return storedValueAmount;
        else
            return new BigDecimal("0");
    }

    public void setStoredValueAmount(BigDecimal storedValueAmount) {
        this.storedValueAmount = storedValueAmount;
    }

    public BigDecimal getMemberCardSellCount() {
        if(memberCardSellCount!=null)
            return memberCardSellCount;
        else
            return new BigDecimal("0");
    }

    public void setMemberCardSell_count(BigDecimal memberCardSellCount) {
        this.memberCardSellCount = memberCardSellCount;
    }

    public Date getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(Date attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    @Override
    public String toString() {
        return "ShiftRecordsBO{" +
                "id=" + id +
                ", classesId=" + classesId +
                ", adminId=" + adminId +
                ", generalIncome=" + generalIncome +
                ", cashIncome=" + cashIncome +
                ", cashBack=" + cashBack +
                ", cashAmount=" + cashAmount +
                ", bankCardIncome=" + bankCardIncome +
                ", bankCardBack=" + bankCardBack +
                ", bankCardAmount=" + bankCardAmount +
                ", wechatIncome=" + wechatIncome +
                ", wechatBack=" + wechatBack +
                ", wechatAmount=" + wechatAmount +
                ", alipayIncome=" + alipayIncome +
                ", alipayBack=" + alipayBack +
                ", alipayAmount=" + alipayAmount +
                ", integralIncome=" + integralIncome +
                ", integralSpend=" + integralSpend +
                ", integralAmount=" + integralAmount +
                ", storedValueIncome=" + storedValueIncome +
                ", storedValueBack=" + storedValueBack +
                ", storedValueAmount=" + storedValueAmount +
                ", memberCardSellCount=" + memberCardSellCount +
                ", attendanceTime=" + attendanceTime +
                ", closingTime=" + closingTime +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", classesName='" + classesName + '\'' +
                '}';
    }
}

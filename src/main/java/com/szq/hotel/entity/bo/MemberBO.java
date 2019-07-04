package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.util.Date;

public class MemberBO extends BaseModel {
    private Integer id;//
    private Integer memberCardId;//会员卡id
    private String phone;//手机号
    private String certificateType;//证件类型
    private String certificateNumber;//证件号
    private Integer integral;//积分
    private Integer storedValue;//储值
    private Integer createUserId;//操作人id
    private String name;//会员名字
    private String birthday;//生日
    private String gender;//性别（man/woman）
    private String address;//地址
    private String remark;//备注
    private String salesman;//销售员
    private String state;//状态
    private Date createTime;//创建时间
    private Integer updateUserId;//修改人id
    private Date updateTime;//修改时间
    private Integer pageSize;//分页 每页的数量
    private Integer pageNo;//分页 页码
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberCardId() {
        return memberCardId;
    }

    public void setMemberCardId(Integer memberCardId) {
        this.memberCardId = memberCardId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getStoredValue() {
        return storedValue;
    }

    public void setStoredValue(Integer storedValue) {
        this.storedValue = storedValue;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}

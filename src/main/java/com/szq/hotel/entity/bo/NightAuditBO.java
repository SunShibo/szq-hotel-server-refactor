package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.util.Date;

public class NightAuditBO extends BaseModel{
    private Integer  id;
    private Integer childId;
    private Integer hotelId;
    private Date createTime;
    private Integer personNum;
    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "NightAuditBO{" +
                "id=" + id +
                ", childId=" + childId +
                ", hotelId=" + hotelId +
                ", createTime=" + createTime +
                ", personNum=" + personNum +
                ", source='" + source + '\'' +
                '}';
    }
}

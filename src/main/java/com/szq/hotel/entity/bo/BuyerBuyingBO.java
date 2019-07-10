package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * 挂账
 */
public class BuyerBuyingBO extends BaseModel{
    private Integer id;
    private String roomName;
    private String name;
    private String phone;
    private Date  createTime;


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

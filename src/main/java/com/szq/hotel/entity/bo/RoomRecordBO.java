package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.util.Date;

public class RoomRecordBO extends BaseModel {
    private Integer id;

    private Integer roomId;

    private String virginState;

    private String newState;

    private String remark;

    private Integer createUserId;

    private Date createTime;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getVirginState() {
        return virginState;
    }

    public void setVirginState(String virginState) {
        this.virginState = virginState == null ? null : virginState.trim();
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState == null ? null : newState.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}
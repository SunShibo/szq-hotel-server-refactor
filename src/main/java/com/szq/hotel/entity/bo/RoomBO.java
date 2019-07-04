package com.szq.hotel.entity.bo;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * 房间信息
 * */
public class RoomBO {
    private Integer id;//房间
    private String roomName;//房间名称
    private Integer roomTypeId;//房型id
    private String roomMajorState;//房间主状态
    private String roomState;//房间状态
    private String remark;//备注
    private String lockRoomState;//锁房状态
    private Date lockRoomStartTime;//锁房开始时间
    private Date lockRoom_endTime;//锁房结束时间
    private String roomAuxiliaryStatus;//房间辅状态
    private String roomAuxiliaryStatusStand;//房间辅状态(备用)
    private String setting;//设置
    private String character;//特性
    private Integer createUserId;//创建人id
    private Date createTime;//创建时间
    private Integer updateUserId;//修改人id
    private Date updateTime;//修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomMajorState() {
        return roomMajorState;
    }

    public void setRoomMajorState(String roomMajorState) {
        this.roomMajorState = roomMajorState;
    }

    public String getRoomState() {
        return roomState;
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLockRoomState() {
        return lockRoomState;
    }

    public void setLockRoomState(String lockRoomState) {
        this.lockRoomState = lockRoomState;
    }

    public Date getLockRoomStartTime() {
        return lockRoomStartTime;
    }

    public void setLockRoomStartTime(Date lockRoomStartTime) {
        this.lockRoomStartTime = lockRoomStartTime;
    }

    public Date getLockRoom_endTime() {
        return lockRoom_endTime;
    }

    public void setLockRoom_endTime(Date lockRoom_endTime) {
        this.lockRoom_endTime = lockRoom_endTime;
    }

    public String getRoomAuxiliaryStatus() {
        return roomAuxiliaryStatus;
    }

    public void setRoomAuxiliaryStatus(String roomAuxiliaryStatus) {
        this.roomAuxiliaryStatus = roomAuxiliaryStatus;
    }

    public String getRoomAuxiliaryStatusStand() {
        return roomAuxiliaryStatusStand;
    }

    public void setRoomAuxiliaryStatusStand(String roomAuxiliaryStatusStand) {
        this.roomAuxiliaryStatusStand = roomAuxiliaryStatusStand;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
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

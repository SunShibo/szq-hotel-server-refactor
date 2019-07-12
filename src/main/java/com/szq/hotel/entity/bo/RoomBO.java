package com.szq.hotel.entity.bo;

import java.util.Date;

public class RoomBO {
    private Integer id;

    private String roomName;

    private Integer roomTypeId;

    private String roomMajorState;

    private String roomState;

    private String remark;

    private String lockRoomState;

    private Date lockRoomStartTime;

    private Date lockRoomEndTime;

    private String roomAuxiliaryStatus;

    private String roomAuxiliaryStatusStand;

    private String setting;

    private String character;

    private Integer createUserId;

    private Date createTime;

    private Integer updateUserId;

    private Date updateTime;

    private Integer floorId;

    private Integer hotelId;
    //楼栋
    private String hotelName;
    //楼层
    private String floorName;
    //房型
    private String roomType;


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
        this.roomName = roomName == null ? null : roomName.trim();
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
        this.roomMajorState = roomMajorState == null ? null : roomMajorState.trim();
    }

    public String getRoomState() {
        return roomState;
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState == null ? null : roomState.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLockRoomState() {
        return lockRoomState;
    }

    public void setLockRoomState(String lockRoomState) {
        this.lockRoomState = lockRoomState == null ? null : lockRoomState.trim();
    }

    public Date getLockRoomStartTime() {
        return lockRoomStartTime;
    }

    public void setLockRoomStartTime(Date lockRoomStartTime) {
        this.lockRoomStartTime = lockRoomStartTime;
    }

    public Date getLockRoomEndTime() {
        return lockRoomEndTime;
    }

    public void setLockRoomEndTime(Date lockRoomEndTime) {
        this.lockRoomEndTime = lockRoomEndTime;
    }

    public String getRoomAuxiliaryStatus() {
        return roomAuxiliaryStatus;
    }

    public void setRoomAuxiliaryStatus(String roomAuxiliaryStatus) {
        this.roomAuxiliaryStatus = roomAuxiliaryStatus == null ? null : roomAuxiliaryStatus.trim();
    }

    public String getRoomAuxiliaryStatusStand() {
        return roomAuxiliaryStatusStand;
    }

    public void setRoomAuxiliaryStatusStand(String roomAuxiliaryStatusStand) {
        this.roomAuxiliaryStatusStand = roomAuxiliaryStatusStand == null ? null : roomAuxiliaryStatusStand.trim();
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting == null ? null : setting.trim();
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character == null ? null : character.trim();
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

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

}
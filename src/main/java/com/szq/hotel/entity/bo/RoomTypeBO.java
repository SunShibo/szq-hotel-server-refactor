package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.util.Date;

public class RoomTypeBO extends BaseModel {
    private Integer id;

    private String roomTypeName;

    private String roomTypePicture;

    private Double basicPrice;

    private Double hourRoomPrice;

    private Integer floorId;

    private Integer createUserId;

    private Date createTime;

    private Integer updateUserId;

    private Date updateTime;

    private Integer hotelId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName == null ? null : roomTypeName.trim();
    }

    public String getRoomTypePicture() {
        return roomTypePicture;
    }

    public void setRoomTypePicture(String roomTypePicture) {
        this.roomTypePicture = roomTypePicture == null ? null : roomTypePicture.trim();
    }

    public Double getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(Double basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Double getHourRoomPrice() {
        return hourRoomPrice;
    }

    public void setHourRoomPrice(Double hourRoomPrice) {
        this.hourRoomPrice = hourRoomPrice;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
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

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
}
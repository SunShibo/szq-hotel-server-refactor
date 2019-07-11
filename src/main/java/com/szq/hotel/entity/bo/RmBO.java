package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

/**
 * @Author: Bin Wang
 * @date: Created in 16:00 2019/7/9
 */
public class RmBO extends BaseModel {
    //房间id
    private Integer id;
    //房间编号
    private String roomName;
    //楼栋
    private String hotelName;
    //楼栋id
    private Integer hotelId;
    //楼层
    private String floorName;
    //楼层id
    private Integer floorId;
    //房间状态
    private String roomMajorState;
    //房间类型名称
    private String roomType;
    //房间类型id
    private Integer roomTypeId;

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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getRoomMajorState() {
        return roomMajorState;
    }

    public void setRoomMajorState(String roomMajorState) {
        this.roomMajorState = roomMajorState;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    @Override
    public String toString() {
        return "RmBO{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", hotelId=" + hotelId +
                ", floorName='" + floorName + '\'' +
                ", floorId=" + floorId +
                ", roomMajorState='" + roomMajorState + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomTypeId=" + roomTypeId +
                '}';
    }
}

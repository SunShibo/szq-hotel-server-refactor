package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

/**
 * @Author: Bin Wang
 * @date: Created in 13:05 2019/7/10
 */
public class RtBO extends BaseModel {
    private String roomTypeName;
    private Integer id;
    private Integer hotelId;
    private Double basicPrice;
    private Double hourRoomPrice;

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
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

    @Override
    public String toString() {
        return "RtBO{" +
                "roomTypeName='" + roomTypeName + '\'' +
                ", id=" + id +
                ", hotelId=" + hotelId +
                ", basicPrice=" + basicPrice +
                ", hourRoomPrice=" + hourRoomPrice +
                '}';
    }
}

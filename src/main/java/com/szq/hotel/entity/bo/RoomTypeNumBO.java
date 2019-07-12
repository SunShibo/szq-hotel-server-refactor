package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

/**
 * @Author: Bin Wang
 * @date: Created in 16:10 2019/7/12
 */
public class RoomTypeNumBO extends BaseModel {

    private Integer count; //酒店数量
    private Integer hotelId;//酒店id
    private Integer id;//id
    private String name;//房型名称
    private Double basicPrice;//全天房价格
    private Double hourRoomPrice;//钟点房价格
    private boolean state;//是否优惠

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "RoomTypeNumBO{" +
                "count=" + count +
                ", hotelId=" + hotelId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", basicPrice=" + basicPrice +
                ", hourRoomPrice=" + hourRoomPrice +
                ", state=" + state +
                '}';
    }
}

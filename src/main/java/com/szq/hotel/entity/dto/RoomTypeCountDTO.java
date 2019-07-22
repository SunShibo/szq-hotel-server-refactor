package com.szq.hotel.entity.dto;

import java.io.Serializable;

/**
 * @Author: Bin Wang
 * @date: Created in 14:53 2019/7/18
 */
public class RoomTypeCountDTO implements Serializable {
    private Integer count;//可用房间数
    private Integer countChinkRoom;//入住中
    private Integer countOrderRoom;//预约中
    private Integer hotelId;//酒店id
    private String name;//房型名称
    private String rotio;//入住率
    private double typePrice;
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCountChinkRoom() {
        return countChinkRoom;
    }

    public void setCountChinkRoom(Integer countChinkRoom) {
        this.countChinkRoom = countChinkRoom;
    }

    public Integer getCountOrderRoom() {
        return countOrderRoom;
    }

    public void setCountOrderRoom(Integer countOrderRoom) {
        this.countOrderRoom = countOrderRoom;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRotio() {
        return rotio;
    }

    public void setRotio(String rotio) {
        this.rotio = rotio;
    }

    public double getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(double typePrice) {
        this.typePrice = typePrice;
    }
}

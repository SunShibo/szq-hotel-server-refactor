package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

/**
 * @Author: Bin Wang
 * @date: Created in 18:25 2019/7/10
 */
public class FlrBO extends BaseModel {
    private Integer id;
    private String name;
    private Integer hotelId;

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

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
}

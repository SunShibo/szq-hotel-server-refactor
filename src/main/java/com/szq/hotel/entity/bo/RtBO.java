package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

/**
 * @Author: Bin Wang
 * @date: Created in 13:05 2019/7/10
 */
public class RtBO extends BaseModel {
    private String roomTypeName;
    private Integer id;

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
}

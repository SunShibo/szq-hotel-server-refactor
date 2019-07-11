package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.util.List;

/**
 * 楼层和房间
 */
public class FloorRoomBO extends BaseModel {

    private Integer id;
    private String name;
    private List<HomeRoomBO> rooms;


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

    public List<HomeRoomBO> getRooms() {
        return rooms;
    }

    public void setRooms(List<HomeRoomBO> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "FloorRoomBO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}

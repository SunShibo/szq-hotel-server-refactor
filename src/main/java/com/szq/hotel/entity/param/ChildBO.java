package com.szq.hotel.entity.param;

import com.szq.hotel.entity.bo.EverydayRoomPriceBO;

import java.util.List;

public class ChildBO {
    private String 房间id;//没有房间id则时根据房型id添加的
    private String 房型id;
    private String 备注;
    List<EverydayRoomPriceBO> 每日房价信息;

    public String get房间id() {
        return 房间id;
    }

    public void set房间id(String 房间id) {
        this.房间id = 房间id;
    }

    public String get房型id() {
        return 房型id;
    }

    public void set房型id(String 房型id) {
        this.房型id = 房型id;
    }

    public String get备注() {
        return 备注;
    }

    public void set备注(String 备注) {
        this.备注 = 备注;
    }

    public List<EverydayRoomPriceBO> get每日房价信息() {
        return 每日房价信息;
    }

    public void set每日房价信息(List<EverydayRoomPriceBO> 每日房价信息) {
        this.每日房价信息 = 每日房价信息;
    }
}

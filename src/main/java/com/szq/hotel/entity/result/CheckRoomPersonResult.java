package com.szq.hotel.entity.result;

import java.util.Date;

//联房信息
public class CheckRoomPersonResult {
    private String name;//姓名
    private Date startTime;//入住时间
    private String roomName;//房间号

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}

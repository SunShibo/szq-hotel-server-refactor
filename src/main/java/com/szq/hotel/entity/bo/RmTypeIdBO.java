package com.szq.hotel.entity.bo;

/**
 * @Author: Bin Wang
 * @date: Created in 17:37 2019/7/31
 */
public class RmTypeIdBO {
    private Integer roomTypeId;
    private String startTime;
    private String endTime;

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

package com.szq.hotel.entity.dto;

import java.io.Serializable;

/**
 * @Author: Bin Wang
 * @date: Created in 11:32 2019/7/11
 */
public class RoomStateDTO implements Serializable {

    private String startDate;
    private String endDate;
    private String state;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

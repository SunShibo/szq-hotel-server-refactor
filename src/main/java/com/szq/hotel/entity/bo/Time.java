package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.util.Date;

/**
 * @Author: Bin Wang
 * @date: Created in 14:11 2019/7/11
 */
public class Time extends BaseModel {

    //一天开始时间
    private Date startTime;
    //一天结束时间
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Time{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

package com.szq.hotel.entity.dto;

import com.szq.hotel.common.base.BaseModel;

/**
 * @Author: Bin Wang
 * @date: Created in 15:37 2019/7/30
 */
public class DateRoomDTO extends BaseModel {
    private String  date; //时间
    private Integer sumRoomType; //总数
    private String typename;  //房间名称
    private Integer usableRoomNunber; //可用

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSumRoomType() {
        return sumRoomType;
    }

    public void setSumRoomType(Integer sumRoomType) {
        this.sumRoomType = sumRoomType;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getUsableRoomNunber() {
        return usableRoomNunber;
    }

    public void setUsableRoomNunber(Integer usableRoomNunber) {
        this.usableRoomNunber = usableRoomNunber;
    }
}

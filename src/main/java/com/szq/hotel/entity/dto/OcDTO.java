package com.szq.hotel.entity.dto;

import com.szq.hotel.common.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Bin Wang
 * @date: Created in 13:58 2019/7/15
 */
public class OcDTO extends BaseModel {
    private Integer id; //子订单id
    private Integer orderId;//订单id
    private Date startTime;//入住时间
    private Date endTime;//离店时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

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
}

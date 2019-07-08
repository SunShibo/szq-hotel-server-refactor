package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.CheckInPersonBO;

import javax.print.DocFlavor;
import java.util.List;

public interface CheckInPersonDAO {
    //根据订单查询入住人信息
    List<CheckInPersonBO> getCheckInPersonById(Integer id);
    //添加入住人信息
    Integer addCheckInPerson(CheckInPersonBO checkInPersonBO);
    //根据子订单删除入住人
    Integer delCheckInPersonById(Integer id);
}

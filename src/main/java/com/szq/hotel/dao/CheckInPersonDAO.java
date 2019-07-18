package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.CheckInPersonBO;
import org.apache.ibatis.annotations.Param;

import javax.print.DocFlavor;
import java.util.List;

public interface CheckInPersonDAO {
    //根据订单查询入住人信息
    List<CheckInPersonBO> getCheckInPersonById(@Param("id") Integer id,@Param("status") String status);
    //添加入住人信息
    Integer addCheckInPerson(CheckInPersonBO checkInPersonBO);
    //根据子订单删除入住人
    Integer delCheckInPersonById(Integer id);

    //检查身份证号是否在住
    Integer checkId(@Param("certificateNumber") String certificateNumber,@Param("orderId") Integer orderId);

    //修改入住人信息
    Integer updCheckInPerson(CheckInPersonBO checkInPersonBO);

    //把入住人修改为已经退房
    Integer updPersonCheckOut(@Param("id") Integer id,@Param("status") String status);
}

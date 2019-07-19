package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.CheckInPersonBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestDAO {

    void updateU1(@Param("id")int id,@Param("num")int  num );

    void updateU2(@Param("id")int id,@Param("num")int  num );

    //根据订单查询入住人信息
    List<CheckInPersonBO> getCheckInPersonById(@Param("id") Integer id, @Param("status") String status);

}

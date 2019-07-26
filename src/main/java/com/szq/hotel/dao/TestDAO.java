package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.CheckInPersonBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestDAO {

    void updateU1(@Param("id")int id,@Param("num")int  num );

    void updateU2(@Param("id")int id,@Param("num")int  num );
}

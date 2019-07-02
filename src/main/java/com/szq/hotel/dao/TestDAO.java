package com.szq.hotel.dao;


import org.apache.ibatis.annotations.Param;

public interface TestDAO {

    void updateU1(@Param("id")int id,@Param("num")int  num );

    void updateU2(@Param("id")int id,@Param("num")int  num );
}

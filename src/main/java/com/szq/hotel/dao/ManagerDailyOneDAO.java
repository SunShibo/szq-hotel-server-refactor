package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.ManagerDailyOneBO;

public interface ManagerDailyOneDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerDailyOneBO record);

    int insertSelective(ManagerDailyOneBO record);

    ManagerDailyOneBO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerDailyOneBO record);

    int updateByPrimaryKey(ManagerDailyOneBO record);
}
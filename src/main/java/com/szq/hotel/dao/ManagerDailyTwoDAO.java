package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.ManagerDailyTwoBO;

public interface ManagerDailyTwoDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerDailyTwoBO record);

    int insertSelective(ManagerDailyTwoBO record);

    ManagerDailyTwoBO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerDailyTwoBO record);

    int updateByPrimaryKey(ManagerDailyTwoBO record);
}
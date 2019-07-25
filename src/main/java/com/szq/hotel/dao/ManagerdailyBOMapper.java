package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.ManagerdailyBO;

public interface ManagerdailyBOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerdailyBO record);

    int insertSelective(ManagerdailyBO record);

    ManagerdailyBO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerdailyBO record);

    int updateByPrimaryKey(ManagerdailyBO record);
}
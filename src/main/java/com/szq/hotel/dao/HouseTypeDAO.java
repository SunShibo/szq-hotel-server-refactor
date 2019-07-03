package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.HouseTypeBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

import java.util.List;

public interface HouseTypeDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(HouseTypeBO record);

    int insertSelective(HouseTypeBO record);

    HouseTypeBO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HouseTypeBO record);

    int updateByPrimaryKeyWithBLOBs(HouseTypeBO record);

    int updateByPrimaryKey(HouseTypeBO record);

    List<HouseTypeBO> queryHouseTypeList(@Param("id")Integer id);

    int updateLevel(@Param("id")Integer id);
}
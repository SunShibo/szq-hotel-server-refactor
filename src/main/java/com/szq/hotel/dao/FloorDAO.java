package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.FloorBO;

import java.util.List;

/**
 * 楼层
 */
public interface FloorDAO {
    /**
     * 添加楼层
     */
    void addFloor(FloorBO floorBO);

    /**
     * 修改楼层
     */
    void updateFloor(FloorBO FloorBO);

    /**
     *  查询楼层
     */
     List<FloorBO>  queryFloor(Integer HotelId);


    /**
     * 查询楼层
     */
     FloorBO  queryFloorById(Integer floorId);

}

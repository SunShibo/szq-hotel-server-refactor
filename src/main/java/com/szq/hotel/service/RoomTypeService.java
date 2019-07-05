package com.szq.hotel.service;

import com.szq.hotel.dao.RoomTypeDAO;
import com.szq.hotel.entity.bo.RoomTypeBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Bin Wang
 * @date: Created in 10:11 2019/7/4
 */
@Service
@Transactional
public class RoomTypeService {

    @Resource
    private RoomTypeDAO roomTypeDAO;

    public List<RoomTypeBO> queryRoomTypeList(Integer id){
        return roomTypeDAO.queryRoomTypeList(id);
    }

    public int insertSelective(RoomTypeBO record){
        return roomTypeDAO.insertSelective(record);
    }

    public int updateRoomType(RoomTypeBO record){
        return roomTypeDAO.updateByPrimaryKeySelective(record);
    }

    public void  deleteRoomType(Integer id){
        roomTypeDAO.updateShow(id);
    }


}

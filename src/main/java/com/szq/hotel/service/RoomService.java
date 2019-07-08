package com.szq.hotel.service;

import com.szq.hotel.dao.RoomDAO;
import com.szq.hotel.entity.bo.RoomBO;
import com.szq.hotel.entity.bo.RoomTypeCountBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Bin Wang
 * @date: Created in 13:00 2019/7/5
 */
@Service
@Transactional
public class RoomService {

    @Resource
    private RoomDAO roomDAO;

    public Map<String, Object> queryRoom(Map<String, Object> map){
        Map<String, Object> mp = new HashMap<String, Object>();
        List<RoomBO> list = roomDAO.queryRoom(map);
        Integer count = roomDAO.queryRoomCount(map);
        mp.put("list",list);
        mp.put("count",count);
        return mp;
    }

    public int insertSelective(RoomBO record){
        return roomDAO.insertSelective(record);
    }

    public int updateByPrimaryKeySelective(RoomBO record){
        return roomDAO.updateByPrimaryKeySelective(record);
    }

    public void deleteByPrimaryKey(Integer[] id){
         roomDAO.updateShow(id);
    }

    public void updatelockRoomClose(Integer[] idArr){
        roomDAO.updatelockRoomState(idArr);
    }

    public void updatelockRoomOpen(Integer[] idArr){
        roomDAO.updatelockRoomState(idArr);
    }

    public List<RoomTypeCountBO> queryRoomTypeCount(Integer id){
        return roomDAO.queryRoomTypeCount(id);
    }

}

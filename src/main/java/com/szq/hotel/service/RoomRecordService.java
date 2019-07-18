package com.szq.hotel.service;

import com.szq.hotel.dao.RoomRecordDAO;
import com.szq.hotel.entity.bo.RoomRecordBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("roomRecordService")
@Transactional
public class RoomRecordService {
    @Resource
    private RoomRecordDAO roomRecordDAO;

    /**
     * 添加房间操作记录
     * @param roomId 房间id
     * @param virginState 原状态
     * @param newState 新状态
     * @param remark 备注
     * @param userId 创建人id
     * @param now 当前时间
     * @return
     */
    public int insert(Integer roomId,String virginState,String newState,
                      String remark,Integer userId,Date now){
        RoomRecordBO record = new RoomRecordBO();
        record.setRoomId(roomId);
        record.setVirginState(virginState);
        record.setNewState(newState);
        record.setRemark(remark);
        record.setCreateUserId(userId);
        record.setCreateTime(now);
        return roomRecordDAO.insert(record);
    }
    /*
        查询房间记录
     */
    public List<RoomRecordBO> selectRoomRecord(Map<String,Object> map){
        return roomRecordDAO.selectRoomRecord(map);
    }

    public Integer selectRoomRecordCount(Integer id){
        return roomRecordDAO.selectRoomRecordCount(id);
    }
}

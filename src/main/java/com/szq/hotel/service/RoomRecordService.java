package com.szq.hotel.service;

import com.szq.hotel.dao.RoomRecordDAO;
import com.szq.hotel.dao.TestDAO;
import com.szq.hotel.entity.bo.RoomRecordBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2018/2/28.
 */
@Service("roomRecordService")
@Transactional
public class RoomRecordService {
    @Resource
    private RoomRecordDAO roomRecordDAO;

    /*
        添加房间操作记录
    */
    public int insert(RoomRecordBO record, Integer userId,Date now){

        record.setCreateUserId(userId);
        record.setCreateTime(now);
        return roomRecordDAO.insert(record);
    }

}

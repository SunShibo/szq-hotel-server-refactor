package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.FloorDAO;
import com.szq.hotel.entity.bo.FloorBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 *  楼层
 */
@Service("FloorService")
@Transactional
public class FloorService {
    private static final Logger log = LoggerFactory.getLogger(FloorService.class);

    @Resource
    private FloorDAO floorDAO;

    /**
     * 添加楼层
     */
    public void addFloor(FloorBO floorBO, Integer userId){
        log.info("start================addFloorl");
        floorBO.setCreateUserId(userId);
        floorDAO.addFloor(floorBO);
        log.info("end===================addHotel");
    }

    /**
     *  删除楼层
     */
    public void deleteFloor(Integer floorId,Integer userId) {
        log.info("start================deleteFloor");
        log.info("floorId{}\tuserId{}",floorId,userId);
        FloorBO floorBO = floorDAO.queryFloorById(floorId);
        if(floorBO==null){
            return;
        }
        floorBO.setUpdateUserId(userId);
        floorBO.setStatus(Constants.NO.getValue());
        floorDAO.updateFloor(floorBO);
        log.info("end===================deleteHotel");
    }

    /**
     * 修改楼层
     */
    public void updateFloor(FloorBO  floorBO,Integer userId){
        log.info("start================updateFloor");
        log.info("param{}\tUserId{}",floorBO,userId);
        floorBO.setCreateUserId(userId);
        floorDAO.updateFloor(floorBO);
        log.info("end===================updateHotel");
    }


    /**
     *  查询楼层
     */
    public List<FloorBO> queryFloorByHotelId(Integer hotelId){
        return floorDAO.queryFloor(hotelId);
    }

}
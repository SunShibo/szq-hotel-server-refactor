package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.HotelDAO;
import com.szq.hotel.entity.bo.ClassesBO;
import com.szq.hotel.entity.bo.CommonBO;
import com.szq.hotel.entity.bo.HotelBO;
import com.szq.hotel.web.controller.HotelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 *  酒店
 */
@Service("HotelService")
@Transactional
public class HotelService {
    private static final Logger log = LoggerFactory.getLogger(HotelController.class);

    @Resource
    private HotelDAO hotelDAO;
    @Resource
    private ClassesService  classesService;
    /**
     * 添加酒店
     */
    public void addHotel(HotelBO hotelBO,Integer userId){
        log.info("start================addHotel");
        hotelBO.setCreateUserId(userId);
        hotelDAO.addHotel(hotelBO);
        hotelDAO.addRoleHotel(hotelBO.getId());
        //添加一个默认班次
        ClassesBO classesBO=new ClassesBO();
        classesBO.setClassesName("默认班次");
        classesBO.setStartTime("12:00:00");
        classesBO.setEndTime("00:00:00");
        classesService.addClasses(classesBO,userId,hotelBO.getId());
        log.info("end===================addHotel");
    }

    /**
     *  删除酒店
     */
    public void deleteHotel(Integer hotelId,Integer userId) {
        log.info("start================deleteHotel");
        log.info("hotelId{}\tuserId{}",hotelId,userId);

        HotelBO hotelBO = hotelDAO.queryHotelById(hotelId);
        if(hotelBO==null){
            return;
        }
        hotelBO.setUpdateUserId(userId);
        hotelBO.setStatus(Constants.NO.getValue());
        hotelDAO.updateHotel(hotelBO);
        log.info("end===================deleteHotel");
    }

    /**
     * 修改酒店
     */
    public void updateHotel(HotelBO hotelBO,Integer userId){
        log.info("start================updateHotel");
        log.info("param{}\tUserId{}",hotelBO,userId);
        hotelBO.setUpdateUserId(userId);
        hotelDAO.updateHotel(hotelBO);
        log.info("end===================updateHotel");
    }



    /**
     *  查询酒店
     */
    public List<HotelBO> queryHotel(){
        return hotelDAO.queryHotel();
    }

    /**
     *  查询酒店通过id
     */
    public HotelBO queryHotelById(Integer hotelId){
        return  hotelDAO.queryHotelById(hotelId);
    }

    /**
     * 查询登录酒店
     */
    public List<CommonBO>  queryLoginHotel(){

        return hotelDAO.queryLoginHotel();
    }

}

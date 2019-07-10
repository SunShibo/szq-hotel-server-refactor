package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.RoomTypeBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.RoomTypeService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.apache.ibatis.executor.ReuseExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * @Author: Bin Wang
 * @date: Created in 10:13 2019/7/4
 */
@Controller
@RequestMapping("/roomType")
public class RoomTypeController extends BaseCotroller {

    static final Logger log = LoggerFactory.getLogger(RoomTypeController.class);

    @Resource
    private RoomTypeService roomTypeService;

    @RequestMapping("/queryRoomType")
    public void queryRoomType(HttpServletRequest request, HttpServletResponse response, Integer id){
        AdminBO loginUser = super.getLoginAdmin(request);
        log.info("id:{}",loginUser.getHotelId());
        List<RoomTypeBO> list = roomTypeService.queryRoomTypeList(id,loginUser.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list)) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/insertRoomType")
    public void insertRoomType(HttpServletRequest request, HttpServletResponse response, RoomTypeBO record){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        record.setHotelId(loginAdmin.getHotelId());
        record.setCreateUserId(loginAdmin.getId());
        record.setCreateTime(new Date());
        roomTypeService.insertSelective(record);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updateRoomType")
    public void updateRoomType(HttpServletRequest request, HttpServletResponse response, RoomTypeBO record){
        roomTypeService.updateRoomType(record);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/deleteRoomType")
    public void deleteRoomType(HttpServletRequest request, HttpServletResponse response, Integer id){
        roomTypeService.deleteRoomType(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

}

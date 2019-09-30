package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.FloorBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.FloorService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.StringUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 *  楼层
 */
@Controller
@RequestMapping("/floor")
public class FloorController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(FloorController.class);

    @Resource
    private FloorService  floorService;

    /**
     * 添加楼层
     */
    @RequestMapping("/addFloor")
    public void addHotel(HttpServletRequest request, HttpServletResponse response,FloorBO floorBO){
        try {
            AdminBO loginAdmin = super.getLoginAdmin(request);
            //验证参数
            if (StringUtils.isEmpty(floorBO.getName()) || floorBO.getHotelId()==null ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            floorService.addFloor(floorBO,loginAdmin.getId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addFloorException",e);
        }

    }

    /**
     *  删除楼层
     */
    @RequestMapping("/deleteFloor")
    public void deleteHotel(HttpServletRequest request, HttpServletResponse response,Integer floorId){
        try{
            AdminBO loginAdmin = super.getLoginAdmin(request);
            if(floorId==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            floorService.deleteFloor(floorId,loginAdmin.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            log.error("deleteHotelException",e);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }

    }

    /**
     * 修改楼层
     */
    @RequestMapping("/updateFloor")
    public void updateFloor(HttpServletRequest request, HttpServletResponse response,FloorBO  floorBO){
        try {
            AdminBO loginAdmin = super.getLoginAdmin(request);
            if (floorBO.getId() == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            floorService.updateFloor(floorBO,loginAdmin.getId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("updateHotel",e);
        }
    }


    /**
     *  查询楼层
     */
    @RequestMapping("/queryFloor")
    public void queryHotel(HttpServletRequest request, HttpServletResponse response,Integer hotelId){
        try {
            if(hotelId==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            List<FloorBO> floorBOS = floorService.queryFloorByHotelId(hotelId);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(floorBOS));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryFloorException",e);
        }
    }


}

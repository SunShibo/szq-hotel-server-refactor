package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.UpdatePriceService;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *  改价
 */
@Controller
@RequestMapping("/updatePrice")
public class UpdatePriceController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(UpdatePriceController.class);

    @Resource
    private UpdatePriceService updatePriceService;

    /**
     * 改价
     */
    @RequestMapping("/updatePrice")
    public void updatePrice(HttpServletRequest request, HttpServletResponse response, String phone, Date startTime, Integer dayNum, String typeIds, Integer orderId, String checkType){
        try {
            AdminBO loginAdmin = super.getLoginAdmin(request);
            //验证参数
            if (dayNum==null || StringUtils.isEmpty(typeIds)|| orderId==null || StringUtils.isEmpty(checkType)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            if(startTime==null){
                startTime=new Date();
            }

            List<Map<String, Object>> maps = updatePriceService.updatePrice(phone, startTime, dayNum, typeIds, orderId, checkType, loginAdmin.getHotelId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(maps));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("updatePriceException",e);
        }

    }




    /**
     * 保存改价
     */
    @RequestMapping("/addPrice")
    public void addPrice(HttpServletRequest request, HttpServletResponse response,String data){
        try {
            //验证参数
            if (StringUtils.isEmpty(data)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            updatePriceService.addPrice(data);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("updatePriceException",e);
        }

    }



    /**
     * 查询是入住方式是否符合
     */
    @RequestMapping("/queryCheckType")
    public void queryCheckType(HttpServletRequest request, HttpServletResponse response,String roomIds,String checkType,Date startTime,Integer dayNum){
        try {
            //验证参数
            if (StringUtils.isEmpty(roomIds) || StringUtils.isEmpty(checkType)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            //判断入住方式
            String s = updatePriceService.queryCheckType(checkType, roomIds);
            if(s!=null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001",s));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
        /*    //判断房间可用状态
           boolean b = updatePriceService.upState(startTime, dayNum, roomIds, checkType);
            if(!b){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001","房间数量不足"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }*/

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryCheckTypeException",e);
        }

    }



}
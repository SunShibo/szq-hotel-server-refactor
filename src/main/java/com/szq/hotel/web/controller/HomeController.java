package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.FloorRoomBO;
import com.szq.hotel.entity.bo.HomeRoomTypeBO;
import com.szq.hotel.entity.bo.HomeTypeBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.entity.param.HomeParam;
import com.szq.hotel.service.HomeService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  首页
 */
@Controller
@RequestMapping("/home")
public class HomeController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Resource
    private HomeService homeService;

    /**
     * 首页
     */
    @RequestMapping("/home")
    public void home(HttpServletRequest request, HttpServletResponse response,HomeParam param){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            List<FloorRoomBO> home = homeService.home(loginAdmin.getHotelId(),param.getVacant(), param.getInthe(), param.getTimeout(),
                    param.getDirty(), param.getSubscribe(), param.getDeparture(), param.getMaintain(), param.getShop(),param.getTypes());

            Map<String,Object> resultMap=new HashMap<String, Object>();
            resultMap.put("room",home);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("homeException",e);
        }

    }



    /**
     * 房态数量
     */
    @RequestMapping("/homeCount")
    public void homeCount(HttpServletRequest request, HttpServletResponse response){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            List<HomeRoomTypeBO> homeRoomTypeBOS = homeService.queryRoomTypeNum(loginAdmin.getHotelId());
            HomeTypeBO homeTypeBO = homeService.queryRommStatus(loginAdmin.getHotelId());
            Map<String,Object> resultMap=new HashMap<String, Object>();
            resultMap.put("state",homeTypeBO);
            resultMap.put("roomType",homeRoomTypeBOS);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("homeCountException",e);
        }

    }


}

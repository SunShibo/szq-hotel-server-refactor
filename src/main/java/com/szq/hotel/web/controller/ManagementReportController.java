package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.CashierSummaryService;
import com.szq.hotel.service.ManagementReportService;
import com.szq.hotel.service.TestService;
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
import java.util.HashMap;
import java.util.Map;


//管理层报表
@Controller
@RequestMapping("/managementReport")
public class ManagementReportController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(ManagementReportController.class);
    @Resource
    private ManagementReportService managementReportService;


    @RequestMapping("/test")
    public void queryVersion(String start, String end, HttpServletRequest request, HttpServletResponse response){
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
            //验证参数
            if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end) ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            Map<String,Object> map = new HashMap<String, Object>();
            String startTime  = start + " 04:00:00";
            String endTime = end + " 04:00:00";
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("hotelId",loginAdmin.getHotelId());
            BigDecimal receivableSum = managementReportService.getReceivableSum(map);


            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(receivableSum));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("addHotelException",e);
        }
    }

}





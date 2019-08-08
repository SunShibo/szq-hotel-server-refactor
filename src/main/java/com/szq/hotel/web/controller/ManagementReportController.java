package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.ManagementReportResponseBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.ManagementReportService;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//管理层报表
@Controller
@RequestMapping("/managementReport")
public class ManagementReportController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(ManagementReportController.class);
    @Resource
    private ManagementReportService managementReportService;



    @RequestMapping("/addtest")
    public void addtest(HttpServletResponse response,HttpServletRequest request){
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

            managementReportService.addData(loginAdmin.getHotelId());


        }catch (Exception e){
        e.getStackTrace();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
        super.safeJsonPrint(response, result);
        log.error("addHotelException",e);
    }

    }

    /**
     * 查询管理层报表
     * @param time 终止时间
     * @param response
     * @param request
     */
    @RequestMapping("/getManagementReport")
    public void getManagementReport(Date time,HttpServletResponse response, HttpServletRequest request ){
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
            if (time==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            String today = DateUtils.getStringData(time,"yyyy-MM-dd");
            String lastDay =DateUtils.getLastDay(today);
            String startTime = lastDay+" 03:00:00";
            String endTime = lastDay+" 05:00:00";
            Date startDate = DateUtils.parseDate(startTime,"yyyy-MM-dd HH:mm:ss");
            Date endDate =  DateUtils.parseDate(endTime,"yyyy-MM-dd HH:mm:ss");
            ManagementReportResponseBO managementReportResponseBO =
                    managementReportService.selectManagementReport(startDate,endDate,loginAdmin.getHotelId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(managementReportResponseBO));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);



        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("getManagementReportException",e);
        }
    }

}





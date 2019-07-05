package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.CashierSummaryBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.CashierSummaryService;
import com.szq.hotel.util.JsonUtils;
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
 *  报表
 */
@Controller
@RequestMapping("/statement")
public class StatementController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(StatementController.class);

    @Resource
    private CashierSummaryService cashierSummaryService;

    /**
     * 收银汇总
     */
    @RequestMapping("/queryCashierSummary")
    public void addHotel(HttpServletRequest request, HttpServletResponse response,CashierSummaryBO param){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            //验证参数
            if (param.getStartTime()==null || param.getEndTime()==null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            List<CashierSummaryBO> cashierSummaryBOS = cashierSummaryService.queryCashierSummary(param);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(cashierSummaryBOS));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("addFloorException",e);
        }

    }







}

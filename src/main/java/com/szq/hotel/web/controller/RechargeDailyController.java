package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.RechargeDailyBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.RechargeDailyService;
import com.szq.hotel.service.TestService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
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
 * 充值日报
 */

@Controller
@RequestMapping("/rechargeDaily")
public class RechargeDailyController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(RechargeDailyController.class);

    @Resource
    private RechargeDailyService rechargeDailyService;

    @RequestMapping("/getRechargeDaily")
    public void getRechargeDaily(Date  startTime,Date endTime, HttpServletRequest request, HttpServletResponse response){
        try {
            //参数验证
            if (startTime==null||endTime==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            List<RechargeDailyBO> rechargeDailyBOS=rechargeDailyService.getRechargeDaily(startTime,endTime);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(rechargeDailyBOS));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("selectMemberCardException",e);
        }
    }

}





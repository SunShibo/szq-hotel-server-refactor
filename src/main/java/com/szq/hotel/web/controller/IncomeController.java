package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.*;
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
* 营收
 */
@Controller
@RequestMapping("/income")
public class IncomeController extends BaseCotroller {
    @Resource
    private IncomeService incomeService;
    private static final Logger log = LoggerFactory.getLogger(IncomeController.class);
    @RequestMapping("/getIncome")
    public void getIncome(HttpServletRequest request, HttpServletResponse response, Date date) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request) ;
            if(userInfo == null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            if(date==null){
                date=new Date();
            }
            List<IncomeBO> incomeBOS=incomeService.getIncome(userInfo.getHotelId(),date);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(incomeBOS)) ;
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
        }catch (Exception e){
            log.error("getIncome",e);
        }
    }

    @RequestMapping("/addIncome")
    public void addIncome(){
        incomeService.addIncome(1);
    }
}





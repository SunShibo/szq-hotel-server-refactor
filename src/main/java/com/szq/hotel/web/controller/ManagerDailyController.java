package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.apache.struts.config.BaseConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Bin Wang
 * @date: Created in 15:25 2019/7/23
 */
@Controller
@RequestMapping("/managerDaliy")
public class ManagerDailyController extends BaseCotroller {


    /**
     * 经理日报
     * @param request
     * @param response
     * @param
     */
    @RequestMapping("/queryManagerDaliy")
    public void queryManagerDaliy(HttpServletRequest request, HttpServletResponse response, Date startTime, Date endTime){
        startTime = endTime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.add(Calendar.DATE, 1);//传过来的时间减去一天   
        endTime = calendar.getTime();

        //endTime=calendar.getTime();//获取一年前的时间，或者一个月前的时间  

        AdminBO userBO = super.getLoginUser(request);
        if (userBO == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "请登录"));
            safeTextPrint(response, json);
            return;
        }
        if (endTime == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            safeTextPrint(response, json);
            return;
        }
    }
}

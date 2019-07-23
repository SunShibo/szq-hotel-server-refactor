package com.szq.hotel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Bin Wang
 * @date: Created in 15:25 2019/7/23
 */
@Controller
@RequestMapping("/managerDaliy")
public class ManagerDailyController {


    /**
     * 经理日报
     * @param request
     * @param response
     * @param date
     */
    @RequestMapping("/queryManagerDaliy")
    public void queryManagerDaliy(HttpServletRequest request, HttpServletResponse response, String date){

    }
}

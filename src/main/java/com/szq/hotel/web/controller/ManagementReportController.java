package com.szq.hotel.web.controller;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.CashierSummaryService;
import com.szq.hotel.service.ManagementReportService;
import com.szq.hotel.service.TestService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;


//管理层报表
@Controller
@RequestMapping("/managementReport")
public class ManagementReportController extends BaseCotroller {
    @Resource
    private ManagementReportService managementReportService;
    @Resource
    private CashierSummaryService cashierSummaryService;

    @RequestMapping("/test")
    public void queryVersion(String start, String end, HttpServletRequest request, HttpServletResponse response){

        BigDecimal ReceivableSum = cashierSummaryService.getReceivableSum(start,end);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(ReceivableSum));
        super.safeJsonPrint(response, result);

        return;
    }

}





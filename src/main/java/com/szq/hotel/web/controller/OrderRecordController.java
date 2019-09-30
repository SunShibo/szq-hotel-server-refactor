package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.OrderRecoredBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.OrderRecordService;
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
 *  子订单详情
 */
@Controller
@RequestMapping("/orderRecord")
public class OrderRecordController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(OrderRecordController.class);

    @Resource
    private OrderRecordService orderRecordService;


    /**
     * 查询
     */
    @RequestMapping("/queryOrderRecord")
    public void queryOrderRecord(HttpServletRequest request, HttpServletResponse response,Integer orderChildId){
        try {
            //验证参数
            if (orderChildId==null ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            List<OrderRecoredBO> orderRecoredBOS = orderRecordService.queryOrderRecord(orderChildId);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderRecoredBOS));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addFloorException",e);
        }

    }


}

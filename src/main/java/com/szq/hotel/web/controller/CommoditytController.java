package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.FloorService;
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


/**
 *  商品交易
 */
@Controller
@RequestMapping("/commodity")
public class CommoditytController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(CommoditytController.class);

    @Resource
    private FloorService  floorService;

    /**
     * 添加商品交易
     * @param request
     * @param response
     * @param payType  支付类型
     * @param consumptionType  消费类型
     * @param money      金额
     * @param info       详情
     */
    @RequestMapping("/addCommodity")
    public void addHotel(HttpServletRequest request, HttpServletResponse response, String payType, String consumptionType, BigDecimal money,String info){
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
            if (StringUtils.isEmpty(payType)  || StringUtils.isEmpty(consumptionType) || StringUtils.isEmpty(info) || money==null || money.doubleValue() < 0 ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }


            //判断支付类型



            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
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
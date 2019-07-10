package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.BuyerBuyingBO;
import com.szq.hotel.entity.bo.CommodityBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.CashierSummaryService;
import com.szq.hotel.service.ChildOrderService;
import com.szq.hotel.service.CommodiryService;
import com.szq.hotel.util.IDBuilder;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  商品交易
 */
@Controller
@RequestMapping("/commodity")
public class CommoditytController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(CommoditytController.class);

    @Resource
    private CashierSummaryService  cashierSummaryService;
    @Resource
    private CommodiryService commodiryService;
    @Resource
    private ChildOrderService childOrderService;
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
    public void addCommodity(HttpServletRequest request, HttpServletResponse response, String payType, String consumptionType, BigDecimal money,String info){
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
            if (StringUtils.isEmpty(payType)  || StringUtils.isEmpty(consumptionType) || StringUtils.isEmpty(info) || money==null || money.doubleValue() < 0 ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            String orderNumber = IDBuilder.getOrderNumber();

            Integer id=commodiryService.addCommodiry(payType,consumptionType,money,info,orderNumber,loginAdmin.getId(),loginAdmin.getHotelId());
            //判断支付类型
            cashierSummaryService.addCommodity(payType,money,info,consumptionType,orderNumber,loginAdmin.getId(),loginAdmin.getHotelId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(id));
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






    /**
     * 查询商品交易
     * @param request
     * @param response
     * @param pageNo
     * @param pageSize
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param condition 查询条件
     */
    @RequestMapping("/queryCommodiry")
    public void queryCommodiry(HttpServletRequest request, HttpServletResponse response, Integer pageNo, Integer pageSize, Date startTime,Date endTime,String condition) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}", loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            //计算分页
            Map<String, Object> queryMap = new HashMap<String, Object>();
            QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
            if (queryInfo != null) {
                queryMap.put("pageSize", queryInfo.getPageSize());
                queryMap.put("pageOffset", queryInfo.getPageOffset());
            }
            //入参
            queryMap.put("startTime", startTime);
            queryMap.put("endTime", endTime);
            queryMap.put("condition", condition);
            queryMap.put("hotelId", loginAdmin.getHotelId());

            List<CommodityBO> commodityBOS = commodiryService.queryCommodiry(queryMap);
            int count = commodiryService.queryCommodiryCount(queryMap);
            Map<String,Object>  resultMap=new HashMap<String, Object>();
            resultMap.put("data",commodityBOS);
            resultMap.put("count",count);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryCommodiryException", e);
        }

    }



    /**
     * 商品交易打印
     * @Param id
     * @param request
     */
    @RequestMapping("/queryCommodiryById")
    public void queryCommodiryById(HttpServletRequest request, HttpServletResponse response, Integer id) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}", loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            if (id == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            CommodityBO commodityBO= commodiryService.queryCommodiryById(id);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(commodityBO));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryCommodiryByIdException", e);
        }

    }



    /**
     * 查询挂账信息
     *  @param roomName  房间名称
     */
    @RequestMapping("/querySuspend")
    public void querySuspend(HttpServletRequest request, HttpServletResponse response,String roomName) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}", loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            if (StringUtils.isEmpty(roomName)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            List<BuyerBuyingBO> data=childOrderService.querySuspend(roomName);
            if(data==null|| data.size()<1){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000008"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(data));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("querySuspendException", e);
        }
    }



    /**
     * 挂账
     * @param request
     * @param response
     * @param consumptionType  消费类型
     * @param money      金额
     * @param info       详情
     */
    @RequestMapping("/suspend")
    public void suspend(HttpServletRequest request, HttpServletResponse response, String consumptionType, BigDecimal money,String info,Integer childId){
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
            if ( StringUtils.isEmpty(consumptionType) || StringUtils.isEmpty(info) || money==null || money.doubleValue() < 0 || childId==null  ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            childOrderService.recorded(childId,money,info,consumptionType,loginAdmin.getId(),loginAdmin.getHotelId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("suspendException",e);
        }

    }



}
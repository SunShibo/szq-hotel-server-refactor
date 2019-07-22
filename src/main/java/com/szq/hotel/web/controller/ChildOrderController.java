package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.entity.param.PayTypeBO;
import com.szq.hotel.service.ChildOrderService;
import com.szq.hotel.service.OrderRecordService;
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
import java.util.Map;

@Controller
@RequestMapping("/chilOrder")
public class ChildOrderController extends BaseCotroller {

    private static final Logger log = LoggerFactory.getLogger(ChildOrderController.class);


    @Resource
    private ChildOrderService childOrderService;
    @Resource
    private OrderRecordService  orderRecordService;
    /**
     * 押金
     * @param request
     * @param response
     * @param payType 支付类型
     * @param orderChildId 子订单id
     * @param money  金额
     */
    @RequestMapping("/addCashPledge")
    public void addCashPledge(HttpServletRequest request, HttpServletResponse response, String payType, Integer orderChildId, BigDecimal money,String certificateNumber) {
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
            if (StringUtils.isEmpty(payType)||orderChildId==null||money==null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            childOrderService.addCashPledge(payType,orderChildId,money,loginAdmin.getId(),loginAdmin.getHotelId(),certificateNumber);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("addCashPledgeException", e);
        }
    }


    /**
     * 入账
     * @param request
     * @param response
     * @param orderChildId 子订单iD
     * @param money  金额
     * @param designation 费用名称
     * @param type  消费类型 商品/赔偿/房费
     */
    @RequestMapping("/recorded")
    public void recorded(HttpServletRequest request, HttpServletResponse response,Integer orderChildId, BigDecimal money,String designation,String type) {
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
            if (StringUtils.isEmpty(type)|| StringUtils.isEmpty(designation)||orderChildId==null || money==null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            childOrderService.recorded(orderChildId,money,designation,type,loginAdmin.getId(),loginAdmin.getHotelId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("addCashPledgeException", e);
        }
    }


    /**
     * 冲减
     * @param request
     * @param response
     * @param orderChildId 子订单iD
     * @param money  金额
     * @param remark 备注
     */
    @RequestMapping("/free")
    public void free(HttpServletRequest request, HttpServletResponse response,Integer orderChildId, BigDecimal money,String remark,String type) {
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
            if (StringUtils.isEmpty(remark)|| orderChildId==null || money==null || StringUtils.isEmpty(type)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            childOrderService.free(orderChildId,money,remark,loginAdmin.getId(),loginAdmin.getHotelId(),type);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("freeException", e);
        }
    }


    /**
     * 子订单转账
     * @param request
     * @param response
     * @param shiftToId 转入子订单
     * @param rollOutId 转出子订单
     * @param ids       详情id
     */
    @RequestMapping("/transferAccounts")
    public void transferAccounts(HttpServletRequest request, HttpServletResponse response,Integer shiftToId,Integer rollOutId,String ids) {
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
            if (StringUtils.isEmpty(ids)|| shiftToId==null || rollOutId==null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            boolean lean = orderRecordService.queryInvoicing(ids);
            if(lean){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000009"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            childOrderService.transferAccounts(loginAdmin.getId(),ids,shiftToId,rollOutId);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("transferAccountsException", e);
        }
    }



    /**
     * 子订单结账查询
     * @param request
     * @param response
     * @param ids       详情id
     */
    @RequestMapping("/queryChildleAccounts")
    public void queryChildleAccounts(HttpServletRequest request, HttpServletResponse response,String ids) {
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
            if (StringUtils.isEmpty(ids)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            boolean lean = orderRecordService.queryInvoicing(ids);
            if(lean){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000009"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            Map<String, Object> map = childOrderService.queryChildleAccounts(ids);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryChildleAccountsException", e);
        }
    }


    /**
     * 单项结账
     * @param request
     * @param response
     * @param chilId    子订单id
     * @param ids       详情id
     * @param status    收/退  yes/no
     */
    @RequestMapping("/childleAccounts")
    public void childleAccounts(HttpServletRequest request, HttpServletResponse response, Integer chilId, String ids,String payType, PayTypeBO param,String status) {
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
            if (StringUtils.isEmpty(ids)||chilId==null||StringUtils.isEmpty(status)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            boolean lean = orderRecordService.queryInvoicing(ids);
            if(lean){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000009"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            childOrderService.childleAccounts(loginAdmin.getHotelId(),loginAdmin.getId(),chilId,ids,payType,param,status);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryChildleAccountsException", e);
        }
    }




    /**
     * 结账查询
     * @param request
     * @param response
     */
    @RequestMapping("/queryAccounts")
    public void queryAccounts(HttpServletRequest request, HttpServletResponse response,String ids) {
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
            if (StringUtils.isEmpty(ids)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            boolean lean = childOrderService.ifCheckOut(ids);
            if(lean){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000009"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            Map<String, Object> map = childOrderService.queryAccounts(ids);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryChildleAccountsException", e);
        }
    }


    /**
     * 结账
     * @param request
     * @param response
     * @param ids       子订单ids
     * @param status    收/退  yes/no
     */
    @RequestMapping("/accounts")
    public void accounts(HttpServletRequest request, HttpServletResponse response,String ids,String payType, PayTypeBO param,String status) {
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
            if (StringUtils.isEmpty(ids)||StringUtils.isEmpty(status)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            boolean lean = orderRecordService.queryInvoicing(ids);
            if(lean){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000009"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            childOrderService.accounts(loginAdmin.getHotelId(),loginAdmin.getId(),ids,payType,param,status);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("accountsException", e);
        }
    }
}

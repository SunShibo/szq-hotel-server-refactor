package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.IntegralRecordBO;
import com.szq.hotel.entity.bo.MemberBO;
import com.szq.hotel.entity.bo.StoredValueRecordBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.IntegralRecordService;
import com.szq.hotel.service.MemberService;
import com.szq.hotel.service.StoredValueRecordService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/member")
public class MemberController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Resource
    private MemberService memberService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private StoredValueRecordService storedValueRecordService;

    /**
     * 新增会员
     * @param memberBO
     * @param request
     * @param response
     */
    @RequestMapping("/addMember")
    public void  addmember(MemberBO memberBO, HttpServletRequest request, HttpServletResponse response){
        try {

            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //获取管理员对象
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            //参数验证
            if (StringUtils.isEmpty(memberBO.getName())||StringUtils.isEmpty(memberBO.getCertificateNumber())||StringUtils.isEmpty(memberBO.getPhone())){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }

            memberService.addMember(memberBO,loginAdmin.getId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加会员成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addMemberException",e);
        }
    }

    /**
     * 删除会员
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/deleteMember")
    public void deleteMember(Integer id,HttpServletRequest request, HttpServletResponse response){
        try{

            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            if(id==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            memberService.deleteMember(id);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除会员成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            log.error("deleteMemberException",e);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000006"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }
    }

    /**
     * 修改会员信息
     * @param memberBO
     * @param request
     * @param response
     */
    @RequestMapping("/updateMember")
    public void updateMember(MemberBO memberBO,HttpServletRequest request, HttpServletResponse response){
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
            if (memberBO.getId() == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            memberService.updateMember(memberBO,loginAdmin.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改会员信息成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("updateMemberException",e);
        }

    }

    /**
     * 条件分页查询会员用户信息
     * @param query
     * @param request
     * @param response
     */
    @RequestMapping("/selectMember")
    public void selectMember(String query,Integer pageNo, Integer pageSize,HttpServletRequest request, HttpServletResponse response){
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
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("query",query);

            QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
            if(queryInfo != null){
                map.put("pageSize",queryInfo.getPageSize());
                map.put("pageOffset",queryInfo.getPageOffset());
            }

            List<MemberBO> memberBOS = memberService.selectMember(map);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(memberBOS));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("selectmemberException",e);
        }
    }

    /**
     * 积分增减
     * @param integralChange
     * @param request
     * @param response
     */
    @RequestMapping("/integralChange")
    public void integralChange(Integer id,BigDecimal integralChange,String remark,String type,BigDecimal currentBalance, HttpServletRequest request, HttpServletResponse response){
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
            if (integralChange == null||id==null||StringUtils.isEmpty(remark)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            MemberBO memberBO = memberService.queryMemberById(id);
            memberBO.setIntegral(integralChange);
            memberService.integralChange(memberBO,loginAdmin.getId());

            MemberBO memberBO1 = memberService.queryMemberById(id);
            type="手动添加";
            currentBalance = memberBO1.getIntegral();

            integralRecordService.addIntegralRecord(id,integralChange,remark,type,currentBalance,loginAdmin.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("积分增减成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("integralChangeException",e);
        }

    }

    /**
     * 储值调整
     * @param storedValueChange
     * @param request
     * @param response
     */
    @RequestMapping("/storedValueChange")
    public void storedValueChange(Integer id,BigDecimal storedValueChange,String remark,String type, BigDecimal presenterMoney,HttpServletRequest request, HttpServletResponse response){
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
            if (id==null||storedValueChange == null||remark==null||StringUtils.isEmpty(type)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            MemberBO memberBO = memberService.queryMemberById(id);
            memberBO.setStoredValue(storedValueChange);
            memberService.storedValueChange(memberBO,loginAdmin.getId());


            MemberBO memberBO1 = memberService.queryMemberById(id);
            StoredValueRecordBO storedValueRecordBO = new StoredValueRecordBO();
            storedValueRecordBO.setCheckInMemberId(id);
            storedValueRecordBO.setType(type);
            storedValueRecordBO.setOddNumbers(IDBuilder.getOrderNumber());
            storedValueRecordBO.setStoredValueMoney(storedValueChange);
            storedValueRecordBO.setCurrentBalance(memberBO1.getIntegral());
            storedValueRecordBO.setRemark(remark);
            storedValueRecordBO.setPresenterMoney(presenterMoney);

            storedValueRecordService.addStoredValueRecord(storedValueRecordBO,loginAdmin.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("储值调整成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("storedValueChangeException",e);
        }

    }

}





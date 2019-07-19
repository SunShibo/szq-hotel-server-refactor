package com.szq.hotel.web.controller;
import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.*;
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
    @Resource
    private CashierSummaryService cashierSummaryService;
    @Resource
    private RechargeDailyService rechargeDailyService;
    @Resource
    private MemberCardService memberCardService;
    @Resource
    private MemberLevelService memberLevelService;
    @Resource
    private CommodiryService  commodiryService;

    /**
     * 新增会员
     * @param memberBO
     * @param request
     * @param response
     */
    @RequestMapping("/addMember")
    public void  addmember(String cardNumber,MemberBO memberBO,BigDecimal money,String payType, HttpServletRequest request, HttpServletResponse response){
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

            MemberCardBO memberCardBO = memberCardService.getCardNumber(cardNumber);

            //设置会员的会员卡id
            memberBO.setMemberCardId(memberCardBO.getId());
            //设置默认积分为0
            memberBO.setIntegral(BigDecimal.valueOf(0));
            //设置默认储值为0
            memberBO.setStoredValue(BigDecimal.valueOf(0));

            String memberCardNumber = memberCardBO.getCardNumber();
            //BigDecimal money = memberCardBO.getMoney();
            //修改会员卡的售出时间
            memberCardService.updateSellingTime(memberCardNumber);

            //向数据库中添加数据
            memberService.addMember(memberBO,loginAdmin.getId());

            //添加收银汇总
            cashierSummaryService.addCard(memberBO.getName(),money,payType,IDBuilder.getOrderNumber(),loginAdmin.getId(),loginAdmin.getHotelId());
            //添加商品交易
            commodiryService.addCommodiry(payType,Constants.APPLYCARD.getValue(),money,null,IDBuilder.getOrderNumber(),loginAdmin.getId(),loginAdmin.getHotelId(),null);



            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加会员成功"));
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
     * 获取卡号
     * @param memberCardLevelId
     * @param request
     * @param response
     */
    @RequestMapping("/getMemberCardNumber")
    public void getMemberCardNumber(Integer memberCardLevelId,String phone,String certificateNumber,HttpServletRequest request,HttpServletResponse response){

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
            if (memberCardLevelId == null||StringUtils.isEmpty(phone)||StringUtils.isEmpty(certificateNumber)){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            MemberBO memberBO1 = memberService.selectMemberByPhone(phone);
            MemberBO memberBO2 = memberService.selectMemberByCerNumber(certificateNumber);
            if (memberBO1!=null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000203"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            if (memberBO2!=null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000204"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            //通过会员级别id找到一个会员卡的信息
            MemberCardBO memberCardBO = memberCardService.getMemberNumberMoney(memberCardLevelId);
            if (memberCardBO == null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000205"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("cardNumber",memberCardBO.getCardNumber());
            map.put("monye",memberCardBO.getMoney());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
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
            Integer count = memberService.selectMemberCount(map);

            Map<String,Object> resultMap =new HashMap<String, Object>();
            resultMap.put("memberBOS",memberBOS);
            resultMap.put("count",count);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
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
    public void storedValueChange(Integer id,BigDecimal storedValueChange,String remark,String type, BigDecimal presenterMoney,BigDecimal currentBalance,HttpServletRequest request, HttpServletResponse response){
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
            if (presenterMoney==null){
                memberBO.setStoredValue(storedValueChange);
            }else {
                memberBO.setStoredValue(storedValueChange.add(presenterMoney));
            }
            memberService.storedValueChange(memberBO,loginAdmin.getId());
            MemberCardBO memberCardBO = memberCardService.getCardByMemberId(id);

            MemberBO memberBO1 = memberService.queryMemberById(id);
            //当前余额=余额+赠送金额
            currentBalance = memberBO1.getStoredValue();
            //添加储值记录
            storedValueRecordService.addStoredValueRecord(id,storedValueChange,remark,type,presenterMoney,currentBalance,loginAdmin.getId());
            //收银汇总
            cashierSummaryService.addStored(memberBO1.getName(),storedValueChange,type,IDBuilder.getOrderNumber(),loginAdmin.getId(),loginAdmin.getHotelId());
            //充值日报
            rechargeDailyService.insertRechargeDaily(loginAdmin.getHotelId(),memberCardBO.getCardNumber(),memberBO1.getName(),id,type,storedValueChange,presenterMoney,loginAdmin.getId());

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

    /**
     * 积分储值支付查询接口
     * @param certificateNumber 证件号
     * @param request
     * @param response
     */
    @RequestMapping("/getStoreValueIntegral")
    public void getStoreValueIntegral(String certificateNumber,HttpServletRequest request,HttpServletResponse response){
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
            //参数验证
            if (StringUtils.isEmpty(certificateNumber)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            MemberBO memberBO = memberService.selectMemberByCerNumber(certificateNumber);
            if (memberBO==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("证件号有误"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            //储值
            BigDecimal storeValue = memberBO.getStoredValue();
            //积分
            BigDecimal integral = memberBO.getIntegral();
            //会员卡id
            Integer memberCardId = memberBO.getMemberCardId();
            MemberLevelBO memberLevelBO = memberLevelService.getLevelByCardId(memberCardId);
            //获取消费1元得多少积分
            BigDecimal consumeGetIntegral= memberLevelBO.getConsumeGetIntegral();
            //积分金额 = 积分 * 消费1元得多少积分
            BigDecimal integralMoney = integral.multiply(consumeGetIntegral);

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("storeValue",storeValue);
            map.put("integralMoney",integralMoney);
            map.put("memeberId",memberBO.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("getStoreValueIntegralException",e);
        }
    }

    /**
     * 通过id查找会员卡号级别和积分
     * @param memberId 会员id
     * @param request
     * @param response
     */
    @RequestMapping("/getMemberInfo")
    public void getMemberInfo(Integer memberId,HttpServletRequest request,HttpServletResponse response){
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
            //参数验证
            if (memberId == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            MemberResultBO memberResultBO = memberService.getMemberCardNumber(memberId);


            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(memberResultBO));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("getMemberInfoException",e);
        }
    }

}





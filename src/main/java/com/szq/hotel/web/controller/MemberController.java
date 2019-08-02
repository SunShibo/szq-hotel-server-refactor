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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Resource
    private ChildOrderService childOrderService;

    /**
     * 新增会员
     * @param cardNumber 卡号
     * @param memberBO
     * @param money 金额
     * @param payType 支付方式
     * @param childId 子订单id
     * @param request
     * @param response
     */
    @RequestMapping("/addMember")
    public void  addmember(String cardNumber,MemberBO memberBO,BigDecimal money,String payType,Integer childId, HttpServletRequest request, HttpServletResponse response){
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
            if (StringUtils.isEmpty(memberBO.getName())||StringUtils.isEmpty(memberBO.getCertificateNumber())||StringUtils.isEmpty(memberBO.getPhone())||memberBO.getCertificateType()==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }

                MemberCardBO memberCardBO = memberCardService.getCardNumber(cardNumber);
                if (memberCardBO != null) {
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
                }
            //不是挂账
            if (childId==null) {
                //添加收银汇总
                cashierSummaryService.addCard(memberBO.getName(), money, payType, IDBuilder.getOrderNumber(), loginAdmin.getId(), loginAdmin.getHotelId());
                //添加商品交易
                Integer commodiryId = commodiryService.addCommodiry(payType, Constants.APPLYCARD.getValue(), money, null, IDBuilder.getOrderNumber(), loginAdmin.getId(), loginAdmin.getHotelId(), null);
                //向数据库中添加数据
                memberService.addMember(memberBO, loginAdmin.getId());
                //刚刚生成的商品交易id返回给前端
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(commodiryId));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            //挂账
            }else {
                childOrderService.recorded(childId,money,"办卡",Constants.APPLYCARD.getValue(),loginAdmin.getId(),loginAdmin.getHotelId());

            //向数据库中添加数据
            memberService.addMember(memberBO, loginAdmin.getId());

                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加会员成功!"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addMemberException",e);
        }
    }

    /**
     * 新增获取卡号
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
     * 修改会员获取卡号
     * @param memberCardLevelId
     * @param request
     * @param response
     */
    @RequestMapping("/updateGetMemberCardNumber")
    public void updateGetMemberCardNumber(Integer memberCardLevelId,String certificateNumber,HttpServletRequest request,HttpServletResponse response){

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
            if (memberCardLevelId == null||StringUtils.isEmpty(certificateNumber)){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }



            MemberBO memberBO2 = memberService.selectMemberByCerNumber(certificateNumber);

            if (memberBO2==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000204"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            MemberCardBO memberCardBO = memberCardService.getCardByMemberId(memberBO2.getId());
            if (memberCardBO!=null){
                if (memberCardLevelId==memberCardBO.getMemberLevelId()){
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("cardNumber",memberCardBO.getCardNumber());
                    map.put("money",0);
                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
                    super.safeJsonPrint(response, result);
                    log.info("result{}",result);
                    return;
                }else {
                    //通过会员级别id找到一个会员卡的信息
                    MemberCardBO memberCardBO1 = memberCardService.getMemberNumberMoney(memberCardLevelId);
                    if (memberCardBO1 == null){
                        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000205"));
                        super.safeJsonPrint(response, result);
                        log.info("result{}",result);
                        return;
                    }
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("cardNumber",memberCardBO1.getCardNumber());
                    map.put("monye",memberCardBO1.getMoney());

                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
                    super.safeJsonPrint(response, result);
                    log.info("result{}",result);
                    return;
                }
            }


        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addMemberException",e);
        }
    }

    /**
     * 修改会员信息
     * @param cardNumber 会员卡号
     * @param memberBO
     * @param money 金额
     * @param payType 支付方式
     * @param request
     * @param response
     */
    @RequestMapping("/updateMember")
    public void updateMember(String cardNumber,MemberBO memberBO,BigDecimal money,String payType,Integer childId,HttpServletRequest request, HttpServletResponse response){
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
            if (memberBO.getId() == null||StringUtils.isEmpty(cardNumber)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            MemberCardBO memberCardBO1 = memberCardService.getCardByMemberId(memberBO.getId());
            if (memberCardBO1!=null) {
                //传的卡号和之前的不一样
                if (!cardNumber.equals(memberCardBO1.getCardNumber())) {
                    MemberCardBO memberCardBO = memberCardService.getCardNumber(cardNumber);
                    if (memberCardBO != null) {
                        //设置会员的会员卡id
                        memberBO.setMemberCardId(memberCardBO.getId());

                        String memberCardNumber = memberCardBO.getCardNumber();
                        //BigDecimal money = memberCardBO.getMoney();
                        //修改会员卡的售出时间
                        memberCardService.updateSellingTime(memberCardNumber);
                    }

                    //不是挂账
                    if (childId==null) {
                        //添加商品交易
                        commodiryService.updateMemberAddCommodiry(payType, Constants.APPLYCARD.getValue(), money, null, IDBuilder.getOrderNumber(), loginAdmin.getId(), loginAdmin.getHotelId(), memberBO.getId());
                        //添加收银汇总
                        cashierSummaryService.addCard(memberBO.getName(), money, payType, IDBuilder.getOrderNumber(), loginAdmin.getId(), loginAdmin.getHotelId());
                    }else {
                        //挂账
                        childOrderService.recorded(childId,money,"办卡",Constants.APPLYCARD.getValue(),loginAdmin.getId(),loginAdmin.getHotelId());
                    }
                    //修改会员卡id
                    memberService.updateMember(memberBO, loginAdmin.getId());

                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改会员信息成功！"));
                    super.safeJsonPrint(response, result);
                    log.info("result{}",result);
                    return;
                }
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
     * 通过证件号手机号查询会员
     * @param phone 手机号
     * @param certificateNumber 证件号
     * @param request
     * @param response
     */
    @RequestMapping("/selectMemberByNumber")
    public void selectMember(String phone,String certificateNumber,HttpServletRequest request, HttpServletResponse response){
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
            //参数验证
            if (StringUtils.isEmpty(phone)&StringUtils.isEmpty(certificateNumber)){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            //MemberBO memberBO = new MemberBO();
            if (certificateNumber==null){
                MemberBO memberBO1 = memberService.selectMemberByPhoneNum(phone);
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(memberBO1));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            MemberBO memberBO = memberService.selectMemberByCertificateNumber(certificateNumber);
            if (memberBO==null){
                MemberBO memberBO1 = memberService.selectMemberByPhoneNum(phone);
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(memberBO1));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(memberBO));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("selectMemberByNumberException",e);
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
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000204"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            MemberResultBO memberResultBO = memberService.getMemberCardNumber(memberBO.getId());
            if (memberResultBO!=null){
                if ("no".equals(memberResultBO.getType())){
                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000207"));
                    super.safeJsonPrint(response, result);
                    log.info("result{}",result);
                    return;
                }
            }
            //储值
            BigDecimal storeValue = memberBO.getStoredValue();
            //积分
            BigDecimal integral = memberBO.getIntegral();
            //会员卡id
            Integer memberCardId = memberBO.getMemberCardId();
            MemberLevelBO memberLevelBO = memberLevelService.getLevelByCardId(memberCardId);
            if (memberLevelBO!=null) {
                //获取消费1元得多少积分
                BigDecimal consumeGetIntegral = memberLevelBO.getConsumeGetIntegral();
                //积分金额 = 积分 * 消费1元得多少积分(保留两位小数)
                BigDecimal integralMoney = integral.multiply(consumeGetIntegral).setScale(2,BigDecimal.ROUND_HALF_UP);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("storeValue", storeValue);
                map.put("integralMoney", integralMoney);
                map.put("memeberId", memberBO.getId());
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000206"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
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

    /**
     * Excel导入会员
     */
    @RequestMapping("/importMember")
    public void importMember(@RequestParam(value="file",required = false)MultipartFile file, HttpServletRequest  request, HttpServletResponse  response) {
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
            //参数验证
            if (file == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            String json = memberService.readExcelFile(file,loginAdmin.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(json));
            super.safeJsonPrint(response, result);

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("importMemberException", e);
        }
    }

    /**
     * 导出会员
     * @return
     */
    @RequestMapping("/exportMember")
    public void download(HttpServletResponse response, HttpServletRequest request){
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
            ServletOutputStream out=response.getOutputStream();
            try {
                SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyyMMddHHmmss");
                Date curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
                String   str   =   formatter.format(curDate);
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("会员信息" +str+ ".xls", "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            String[] titles = { "卡号", "注册分店", "姓名", "会员级别" ,"生日","手机号","会员折扣","储值总金额","储值余额","累计积分","已兑积分","剩余积分","销售员","发卡日期","证件类型","证件号"};
            memberService.export(titles, out,loginAdmin.getHotelId());
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("导出成功！"));
            safeTextPrint(response, json);
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("exportMemberException", e);
        }
    }

    /**
     * 查询会员消费明细
     * @param memberId 会员id
     * @param request
     * @param response
     */
    @RequestMapping("/getConsumptionRecord")
    public void getConsumptionRecord(Integer memberId,Integer pageNo, Integer pageSize,HttpServletRequest request, HttpServletResponse response){
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
            //参数验证
            if (memberId==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("memberId",memberId);

            QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
            if(queryInfo != null){
                map.put("pageSize",queryInfo.getPageSize());
                map.put("pageOffset",queryInfo.getPageOffset());
            }

            List<ConsumptionRecordBO> consumptionRecordBOS=memberService.getConsumptionRecord(map);
            Integer count = memberService.getConsumptionRecordCount(map);
            Map<String,Object> resultMap =new HashMap<String, Object>();
            resultMap.put("consumptionRecordBOS",consumptionRecordBOS);
            resultMap.put("count",count);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("getConsumptionRecordException",e);
        }
    }

}





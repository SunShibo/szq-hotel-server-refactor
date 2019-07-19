package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.MemberCardDAO;
import com.szq.hotel.dao.MemberDAO;
import com.szq.hotel.entity.bo.MemberBO;
import com.szq.hotel.entity.bo.MemberCardBO;
import com.szq.hotel.entity.bo.MemberLevelBO;
import com.szq.hotel.entity.bo.MemberResultBO;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.ReadCardExcelUtil;
import com.szq.hotel.util.ReadMemberExcelUtil;
import com.szq.hotel.web.controller.MemberController;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service("memberService")
@Transactional
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Resource
    private MemberDAO memberDAO;
    @Resource
    private MemberCardDAO memberCardDAO;
    @Resource
    private StoredValueRecordService storedValueRecordService;
    @Resource
    private MemberLevelService memberLevelService;
    @Resource
    private IntegralRecordService integralRecordService;

    /*
        新增会员
     */
    public void addMember(MemberBO memberBO,Integer userId){
        log.info("start================addMember");
        memberBO.setCreateUserId(userId);
        memberDAO.addMember(memberBO);
        log.info("end================addMember");
    }
    /*
        删除会员信息
     */
    public void deleteMember(Integer id){
        log.info("start================deleteMember");
        log.info("id{}",id);

        MemberBO memberBO = memberDAO.queryMemberById(id);
        if(memberBO==null){
            return;
        }
        memberBO.setState(Constants.NO.getValue());
        memberDAO.updateMember(memberBO);
        log.info("end===================deleteMember");
    }
    /*
        修改会员信息
     */
    public void updateMember(MemberBO memberBO,Integer userId){
        log.info("start================updateMember");
        log.info("param{}\tUserId{}",memberBO,userId);
        memberBO.setUpdateUserId(userId);
        memberDAO.updateMember(memberBO);
        log.info("end===================updateMember");
    }

    /*
        条件分页查询会员信息
     */
    public List<MemberBO> selectMember(Map<String,Object> map){
        return memberDAO.selectMember(map);
    }
    /*
        条件分页查询会员信息数量
     */
    public Integer selectMemberCount(Map<String,Object> map){
        return memberDAO.selectMemberCount(map);
    }


    /*
        通过id查询会员信息
     */
    public MemberBO queryMemberById(Integer id){
        return memberDAO.queryMemberById(id);
    }
        /*
            积分增减
         */
    public void integralChange(MemberBO memberBO,Integer userId){
        log.info("start================integralChange");
        log.info("param{}\tUserId{}",memberBO,userId);
        memberBO.setUpdateUserId(userId);
        memberDAO.integralChange(memberBO);
        log.info("end===================integralChange");
    }

    /**
     *  结账增加积分
     * @param memberId 会员id
     * @param money 金额
     * @param remark 备注
     * @param userId 操作人id
     */
    public void accountIntegral(Integer memberId,BigDecimal money,String remark,Integer userId){
        //通过id查询会员信息
        MemberBO memberBO = memberDAO.queryMemberById(memberId);
        Integer memberCardId = memberBO.getMemberCardId();
        //查询改会员所属会员级别
        MemberLevelBO memberLevelBO = memberLevelService.getLevelByCardId(memberCardId);
        BigDecimal consumeGetIntegral = memberLevelBO.getConsumeGetIntegral();
        //积分数 = 消费金额 * 比例
        BigDecimal integral = money.multiply(consumeGetIntegral);
        memberBO.setIntegral(integral);
        memberBO.setUpdateUserId(userId);
        memberDAO.integralChange(memberBO);
        //查询最新
        MemberBO memberBO1 = memberDAO.queryMemberById(memberId);
        String type = "结账增加";
        integralRecordService.addIntegralRecord(memberId,integral,remark,type,memberBO1.getIntegral(),userId);

    }

    /**
     * 积分减免
     * @param certificateNumber 证件号
     * @param subtractMoney 减免多少钱
     * @param remark 备注
     * @param type 类型
     * @param userId 操作人id
     */
    public void integralBreaks(String certificateNumber,BigDecimal subtractMoney,String remark,String type,Integer userId){
        //通过证件号获取会员对象
        MemberBO memberBO = this.selectMemberByCerNumber(certificateNumber);
        Integer cardId = memberBO.getMemberCardId();
        //通过会员卡id查找会员级别对象
        MemberLevelBO memberLevelBO = memberLevelService.getLevelByCardId(cardId);
        //得到消费1元获得多少积分
        BigDecimal consumeGetIntegral = memberLevelBO.getConsumeGetIntegral();
        //得出应该减多少积分
        BigDecimal integral = subtractMoney.divide(consumeGetIntegral);
        Map<String,Object> map = new HashMap<String, Object>();
        //进行封装
        map.put("certificateNumber",certificateNumber);
        map.put("integral",integral);
        memberDAO.integralBreaks(map);
        //查询最新
        MemberBO memberBO1 = this.selectMemberByCerNumber(certificateNumber);
        //添加到积分明细
        integralRecordService.addIntegralRecord(memberBO.getId(),integral,remark,type,memberBO1.getIntegral(),userId);


    }
    /*
        储值调整
     */
    public void storedValueChange(MemberBO memberBO,Integer userId){
        log.info("start================storedValueChange");
        log.info("param{}\tUserId{}",memberBO,userId);
        memberBO.setUpdateUserId(userId);
        memberDAO.storedValueChange(memberBO);
        log.info("end===================storedValueChange");
    }
    /*
        结账退储值
     */
    public void accountStoreValue(Integer memberId,BigDecimal money,String remark,Integer userId){
       MemberBO memberBO =  memberDAO.queryMemberById(memberId);
       memberBO.setUpdateUserId(userId);
       memberBO.setStoredValue(money);
       memberDAO.storedValueChange(memberBO);

       MemberBO memberBO1 = memberDAO.queryMemberById(memberId);

       String type = "结账退款";
       storedValueRecordService.addStoredValueRecord(memberId,money,remark,type,null,memberBO1.getStoredValue(),userId);
    }

    /**
     * 储值消费
     * @param id 会员id
     * @param subtractMoney 消费金额
     * @param remark 备注
     * @param type 类型
     * @param presenterMoney 赠送金额
     * @param currentBalance  当前余额
     * @param userId 操作人id
     */
    public void storedValueSubtract(Integer id,BigDecimal subtractMoney,String remark,String type,BigDecimal presenterMoney,BigDecimal currentBalance, Integer userId){
        MemberBO memberBO = new MemberBO();
        memberBO.setId(id);
        memberBO.setStoredValue(subtractMoney);
        memberDAO.storedValueSubtract(memberBO);

        storedValueRecordService.addStoredValueRecord(id,subtractMoney,remark,type,presenterMoney,currentBalance,userId);

    }

    /**
     * 储值支付
     * @param certificateNumber 证件号
     * @param subtractMoney 消费金额
     * @param remark 备注
     * @param type 类型
     * @param presenterMoney 赠送金额
     * @param userId 操作人id
     */
    public void storedValuePay(String certificateNumber,BigDecimal subtractMoney,String remark,String type,BigDecimal presenterMoney,Integer userId){
        MemberBO memberBO = this.selectMemberByCerNumber(certificateNumber);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("certificateNumber",certificateNumber);
        map.put("subtractMoney",subtractMoney);
        memberDAO.storedValuePay(map);
        MemberBO memberBO1 = this.selectMemberByCerNumber(certificateNumber);

        storedValueRecordService.addStoredValueRecord(memberBO.getId(),subtractMoney,remark,type,presenterMoney,memberBO1.getStoredValue(),userId);

    }

    /*
       通过手机号查询会员信息
    */
    public MemberBO selectMemberByPhone(String phone){
        return memberDAO.selectMemberByPhone(phone);
    }

    /*
        通过证件号号查询会员信息
     */
    public MemberBO selectMemberByCerNumber(String certificateNumber){
        return memberDAO.selectMemberByCerNumber(certificateNumber);
    }
    /*
        通过证件号查询会员id
     */
    Integer getIdByCerNumber(String certificateNumber){
        return memberDAO.getIdByCerNumber(certificateNumber);
    }
    /*
        通过证件号查询储值和积分金额
     */
    public MemberBO getStoreValueIntegral(String certificateNumber){
        return memberDAO.getStoreValueIntegral(certificateNumber);
    }
    /*
        通过id查找会员卡号级别和积分
     */
    public MemberResultBO getMemberCardNumber(Integer memberId){
        return memberDAO.getMemberCardNumber(memberId);
    }

    /**
     * Excel 导入会员
     *
     * @param
     */
    public String readExcelFile(MultipartFile file,Integer userId) {
        String result = "";
        //创建处理EXCEL的类
        ReadMemberExcelUtil readMemberExcelUtil = new ReadMemberExcelUtil();
        //解析excel，获取上传的事件单
        List<Map<String, Object>> memberList = readMemberExcelUtil.getExcelInfo(file);
        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
        MemberBO memberBO = new MemberBO();
        MemberCardBO memberCardBO = new MemberCardBO();
        List<String> list = new ArrayList<String>();
        for(Map<String, Object> member:memberList) {
            memberCardBO.setCardNumber(member.get("cardNumber").toString());
            list.add(memberCardBO.getCardNumber());
            MemberCardBO memberCardBO1 = memberCardDAO.getCardNumber(member.get("cardNumber").toString());
            memberBO.setMemberCardId(memberCardBO1.getId());
            //TODO
        }
        List<MemberCardBO> memberCardBOS = memberCardDAO.getCartByCartList(list);



        if (memberCardBOS.size()==0){
            result = "会员卡号不存在";
        }else {
            for(Map<String, Object> member:memberList) {
                memberBO.setPhone(member.get("phone").toString());
                memberBO.setName(member.get("name").toString());
                memberBO.setBirthday(member.get("birthday").toString());
                memberBO.setCertificateType(member.get("certificateType").toString());
                memberBO.setCertificateNumber(member.get("certificateNumber").toString());
                memberBO.setGender(member.get("gender").toString());
                memberBO.setAddress(member.get("address").toString());
                memberBO.setSalesman(member.get("salesman").toString());
                memberBO.setRemark(member.get("remark").toString());
                memberCardBO.setCardNumber(member.get("cardNumber").toString());
                memberCardBO.setSellingTime(DateUtils.parseDate(member.get("sellingTime").toString(),"yyyy-MM-dd HH:mm:ss") );
                memberBO.setCreateUserId(userId);
                int ret = memberDAO.importMember(memberBO);
                if (ret == 0) {
                    result = "插入数据库失败";
                }

            }
            if (memberList != null && !memberList.isEmpty()) {
                result = "上传成功！";
            } else {
                result = "上传失败！";
            }
        }

        return result;
    }

    //通过证件号和手机号查询会员
    public MemberBO getMemberByCerNumber(String phone,String certificateNumber){
        return memberDAO.getMemberByCerNumber(phone,certificateNumber);
    }

}

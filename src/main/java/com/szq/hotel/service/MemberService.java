package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.MemberDAO;
import com.szq.hotel.entity.bo.MemberBO;
import com.szq.hotel.entity.bo.StoredValueRecordBO;
import com.szq.hotel.web.controller.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("memberService")
@Transactional
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Resource
    private MemberDAO memberDAO;
    @Resource
    private StoredValueRecordService storedValueRecordService;

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
}

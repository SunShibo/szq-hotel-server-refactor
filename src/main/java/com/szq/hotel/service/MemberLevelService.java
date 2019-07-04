package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.MemberLevelDAO;
import com.szq.hotel.entity.bo.HotelBO;
import com.szq.hotel.entity.bo.MemberLevelBO;
import com.szq.hotel.web.controller.HotelController;
import com.szq.hotel.web.controller.MemberLevelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("MemberLevelService")
@Transactional
public class MemberLevelService {
    private static final Logger log = LoggerFactory.getLogger(MemberLevelController.class);

    @Resource
    private MemberLevelDAO memberLevelDAO;

    /**
     * 查询所有会员级别
     * @return
     */
   public List<MemberLevelBO> selectMemberLevel(){
       return memberLevelDAO.selectMemberLevel();
    }
    /**
     * 新增会员级别
     * @param memberLevelBO
     * @return
     */
    public void addMemberLevel(MemberLevelBO memberLevelBO,Integer userId){
        log.info("start================addMemberLevel");
        memberLevelBO.setCreateUserId(userId);
        memberLevelDAO.addMemberLevel(memberLevelBO);
        log.info("end================addMemberLevel");
    }

    /**
     * 删除会员级别
     * @param memberLevelId
     * @param userId
     */
    public void deleteMemberLevel(Integer memberLevelId,Integer userId){
        log.info("start================deleteMemberLevel");
        log.info("memberLevelId{}\tuserId{}",memberLevelId,userId);

        MemberLevelBO memberLevelBO = memberLevelDAO.queryMemberLevelById(memberLevelId);
        if(memberLevelBO==null){
            return;
        }
        memberLevelBO.setUpdateUserId(userId);
        memberLevelBO.setState(Constants.NO.getValue());
        memberLevelDAO.updateMemberLevel(memberLevelBO);
        log.info("end===================deleteMemberLevel");
    }
    /**
     * 修改会员级别
     * @param memberLevelBO
     */
    public void updateMemberLevel(MemberLevelBO memberLevelBO,Integer userId){
        log.info("start================updateMemberLevel");
        log.info("param{}\tUserId{}",memberLevelBO,userId);
        memberLevelBO.setUpdateUserId(userId);
        memberLevelDAO.updateMemberLevel(memberLevelBO);
        log.info("end===================updateMemberLevel");
    }
}

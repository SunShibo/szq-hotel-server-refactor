package com.szq.hotel.service;

import com.szq.hotel.dao.MemberRoomTypeDAO;
import com.szq.hotel.entity.bo.MemberLevelBO;
import com.szq.hotel.entity.bo.MemberRoomTypeBO;
import com.szq.hotel.web.controller.MemberRoomTypeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("memberRoomTypeService")
@Transactional
public class MemberRoomTypeService {
    private static final Logger log = LoggerFactory.getLogger(MemberRoomTypeController.class);
    @Resource
    private MemberRoomTypeDAO memberRoomTypeDAO;

    /*
        添加会员房型折扣
     */
    public void addMemberRoomType(MemberRoomTypeBO memberRoomTypeBO,Integer userId){
        log.info("start================addMemberRoomType");
        memberRoomTypeBO.setCreateUserId(userId);
        memberRoomTypeDAO.addMemberRoomType(memberRoomTypeBO);
        log.info("end================addMemberRoomType");
    }

    /*
        修改会员折扣
     */
    public void updateMemberRoomType(MemberLevelBO memberLevelBO, Integer userId){
        log.info("start================updateMemberRoomType");
        memberLevelBO.setUpdateUserId(userId);
        memberRoomTypeDAO.updateMemberRoomType(memberLevelBO);
        log.info("end================updateMemberRoomType");
    }

    /*
        查询会员房型折扣
     */
    public MemberLevelBO selectMemberRoomType(Integer id){
        return memberRoomTypeDAO.selectMemberRoomType(id);
    }
}

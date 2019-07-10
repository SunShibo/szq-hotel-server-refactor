package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.MemberLevelBO;
import com.szq.hotel.entity.bo.MemberRoomTypeBO;


public interface MemberRoomTypeDAO {
    /*
        添加会员房型折扣
     */
    void addMemberRoomType(MemberRoomTypeBO memberRoomTypeBO);
    /*
        修改会员折扣
     */
    void updateMemberRoomType(MemberLevelBO memberLevelBO);
    /*
        查询会员房型折扣
     */
    MemberLevelBO selectMemberRoomType(Integer id);
}

package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.MemberRoomTypeBO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface MemberRoomTypeDAO {
    /*
        添加会员房型折扣
     */
    void addMemberRoomType(MemberRoomTypeBO memberRoomTypeBO);
    /*
        修改会员房型折扣
     */
    void updateMemberRoomType(MemberRoomTypeBO memberRoomTypeBO);
    /*
        查询会员房型折扣
     */
    BigDecimal selectMemberRoomType(@Param("memberLevelId") Integer memberLevelId, @Param("roomTypeId")Integer roomTypeId);
}

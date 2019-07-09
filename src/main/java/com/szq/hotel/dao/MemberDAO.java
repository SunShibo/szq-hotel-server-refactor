package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.MemberBO;

import java.util.List;
import java.util.Map;

public interface MemberDAO {
    /*
        新增会员
     */
    void addMember(MemberBO memberBO);
    /*
        修改会员信息
     */
    void updateMember(MemberBO memberBO);

    /*
        条件分页查询会员信息
     */
    List<MemberBO> selectMember(Map<String,Object> map);

    /*
        通过id查询会员信息
     */
    MemberBO queryMemberById(Integer id);
    /*
        积分增减
     */
    void integralChange(MemberBO memberBO);

    /*
        储值调整
     */
    void storedValueChange(MemberBO memberBO);

}

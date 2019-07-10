package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.MemberLevelBO;

import java.util.List;

public interface MemberLevelDAO {

    /**
     * 新增会员级别
     * @param memberLevelBO
     * @return
     */
    void addMemberLevel(MemberLevelBO memberLevelBO);

    /**
     * 修改会员级别
     * @param memberLevelBO
     */
    void updateMemberLevel(MemberLevelBO memberLevelBO);
    /**
     * 查询所有会员级别
     * @return
     */
    List<MemberLevelBO> selectMemberLevel();
    /*
        根据名称查询级别信息
     */
    MemberLevelBO selectMemberLevelByName(String name);
    /**
     * 通过id查询会员级别
     * @param memebrLevelId
     * @return
     */
    MemberLevelBO queryMemberLevelById(Integer memebrLevelId);
}

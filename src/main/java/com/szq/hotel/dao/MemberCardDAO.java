package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.MemberCardBO;

import java.util.List;
import java.util.Map;

public interface MemberCardDAO {
    /*
        批量添加会员卡
    */
    void  addMemberCard(Map<String,Object> map);
    /*
        Excel导入会员卡
     */
    void addMemberCardTest(MemberCardBO memberCardBO);
    /*
        查询会员卡存在
     */
    List<MemberCardBO> queryCartByCartList(List<String> list);

    /*
        修改会员卡
     */
    void updateMemberCard(MemberCardBO memberCardBO);
    /*
        通过id查询会员卡
     */
    MemberCardBO queryMemberCardById(Integer id);

    /*
        条件分页查询会员卡
     */
    List<MemberCardBO> selectMemberCard(Map<String,Object> map);
    /*
        查询条数
     */
    Integer getCount(Map<String,Object> map);
}

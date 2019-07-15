package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.MemberCardBO;
import com.szq.hotel.entity.bo.MemberCardResultBO;

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
    Integer addMemberCardTest(MemberCardBO memberCardBO);
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
        条件查询会员卡
     */
    List<MemberCardBO> conditionSelectMemberCard(Map<String,Object> map);
    /*
        导出会员卡
     */
    List<MemberCardResultBO> exportMemberCard(Map<String,Object> map);
    /*
        查询条数
     */
    Integer getCount(Map<String,Object> map);
    /*
        通过会员id查找会员卡信息
     */
    MemberCardBO getCardByMemberId(Integer memberId);
    /*
        查询会员卡卡号和售价
     */
    MemberCardBO getMemberNumberMoney(Integer memberLevelId);
}

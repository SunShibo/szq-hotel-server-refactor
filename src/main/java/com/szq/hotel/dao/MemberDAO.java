package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.DictionaryValueBO;
import com.szq.hotel.entity.bo.ExportMemberResultBO;
import com.szq.hotel.entity.bo.MemberBO;
import com.szq.hotel.entity.bo.MemberResultBO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
        条件分页查询会员信息数量
     */
    Integer selectMemberCount(Map<String,Object> map);

    /*
        通过id查询会员信息
     */
    MemberBO queryMemberById(Integer id);
    /*
        积分增加
     */
    void integralChange(MemberBO memberBO);
    /*
        积分减免
     */
    void integralBreaks(Map<String,Object> map);
    /*
        储值调整
     */
    void storedValueChange(MemberBO memberBO);
    /*
        储值消费
     */
    void storedValueSubtract(MemberBO memberBO);
    /*
        储值支付
     */
    void storedValuePay(Map<String,Object> map);
    /*
        通过手机号查询会员信息
     */
    MemberBO selectMemberByPhone(String phone);
    /*
        通过证件号号查询会员信息
     */
    MemberBO selectMemberByCerNumber(String certificateNumber);
    /*
        通过证件号查询会员id
     */
    Integer getIdByCerNumber(String certificateNumber);
    /*
        通过证件号查询储值和积分金额
     */
    MemberBO getStoreValueIntegral(String certificateNumber);
    /*
        通过id查找会员卡号级别和积分
     */
    MemberResultBO getMemberCardNumber(Integer memberId);

    //Excel导入会员
    Integer importMember(MemberBO memberBO);

    //Excel导出会员
    List<ExportMemberResultBO> exportMember();

    //通过证件号和手机号查询会员
    MemberBO getMemberByCerNumber(@Param("phone")String phone,@Param("certificateNumber") String certificateNumber);

    /*
        通过证件号查询会员是否存在
     */
    List<MemberBO> getMemberByNumberList(List<String> list);

    /*
        通过手机号查询会员是否存在
     */
    List<MemberBO> getMemberByPhoneList(List<String> list);

    //通过value查询证件类型是否存在
    List<DictionaryValueBO> getCertificateTypeList(List<String> list);



    //  总储值
    BigDecimal  getMemberSumStoreValue(Integer memberId);
    //  已对积分
    BigDecimal  getConversionIntegral(Integer memberId);
    // 累计积分
    BigDecimal  getSumIntegral(Integer memberId);
    //查询证件类型
    String getValue(Integer valueId);
    //通过value查询证件类型id
    Integer getValueId(String value);
}

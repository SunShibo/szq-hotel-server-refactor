package com.szq.hotel.service;

import com.szq.hotel.dao.RechargeDailyDAO;
import com.szq.hotel.entity.bo.RechargeDailyBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("rechargeDailyService")
@Transactional
public class RechargeDailyService {
    @Resource
    private RechargeDailyDAO rechargeDailyDAO;

    /**
     * 添加充值日报
     * @param hotelId 酒店id
     * @param memberCardNumber 卡号
     * @param name 名字
     * @param memberId 会员id
     * @param payType 支付方式
     * @param rechargeMoney 充值金额
     * @param presenterMoney 赠送金额
     * @param createUserId 操作人id
     */
    public void insertRechargeDaily(Integer hotelId,String memberCardNumber,String name,Integer memberId,String payType,BigDecimal rechargeMoney,BigDecimal presenterMoney,Integer createUserId){
        RechargeDailyBO rechargeDailyBO = new RechargeDailyBO();
        rechargeDailyBO.setHotelId(hotelId);
        rechargeDailyBO.setMemberCardNumber(memberCardNumber);
        rechargeDailyBO.setName(name);
        rechargeDailyBO.setMemberId(memberId);
        rechargeDailyBO.setPayType(payType);
        rechargeDailyBO.setRechargeMoney(rechargeMoney);
        rechargeDailyBO.setPresenterMoney(presenterMoney);
        rechargeDailyBO.setCreateUserId(createUserId);
        rechargeDailyDAO.insertRechargeDaily(rechargeDailyBO);

    }

    /*
       获取充值日报
    */
    public List<RechargeDailyBO> getRechargeDaily(Date begin, Date end){
        return rechargeDailyDAO.getRechargeDaily(begin,end);
    }
}

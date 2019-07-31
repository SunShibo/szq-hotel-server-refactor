package com.szq.hotel.service;

import com.szq.hotel.dao.CheckInPersonDAO;
import com.szq.hotel.entity.bo.CheckInPersonBO;
import com.szq.hotel.entity.bo.CommodityBO;
import com.szq.hotel.entity.bo.CommonBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */
@Service("CheckInPersonService")
@Transactional
public class CheckInPersonService {
    @Resource
    private CheckInPersonDAO checkInPersonDAO;



    //检查身份证号是否在住
    public Integer checkId(String certificateNumber,Integer orderChildId){
        return checkInPersonDAO.checkId(certificateNumber,orderChildId);
    }

    //添加入住人信息
    public Integer addCheckInPerson(Integer orderChildId,String name,String gender,String phone,String certificateNumber,Integer certificateType,String status,String remark,Integer userId){
        CheckInPersonBO checkInPersonBO = new CheckInPersonBO();
        checkInPersonBO.setOrderChildId(orderChildId);
        checkInPersonBO.setName(name);
        checkInPersonBO.setGender(gender);
        checkInPersonBO.setPhone(phone);
        checkInPersonBO.setCertificateNumber(certificateNumber);
        checkInPersonBO.setCertificateType(certificateType);
        checkInPersonBO.setStatus(status);
        checkInPersonBO.setRemark(remark);
        checkInPersonBO.setCreateUserId(userId);
        return checkInPersonDAO.addCheckInPerson(checkInPersonBO);
    }

    //修改入住人信息
    public Integer updCheckInPerson(Integer id,String name,String gender,String phone,String certificateNumber,Integer certificateType,String status,String remark){
        CheckInPersonBO checkInPersonBO = new CheckInPersonBO();
        checkInPersonBO.setId(id);
        checkInPersonBO.setName(name);
        checkInPersonBO.setGender(gender);
        checkInPersonBO.setPhone(phone);
        checkInPersonBO.setCertificateNumber(certificateNumber);
        checkInPersonBO.setCertificateType(certificateType);
        checkInPersonBO.setStatus(status);
        checkInPersonBO.setRemark(remark);
        return checkInPersonDAO.updCheckInPerson(checkInPersonBO);
    }

    //根据订单查询入住人信息
    public List<CheckInPersonBO> getCheckInPersonById(Integer id,String status){
        return checkInPersonDAO.getCheckInPersonById(id,status);
    }
}

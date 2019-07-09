package com.szq.hotel.service;

import com.szq.hotel.dao.CheckInPersonDAO;
import com.szq.hotel.dao.TestDAO;
import com.szq.hotel.entity.bo.CheckInPersonBO;
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

    //根据订单查询入住人信息
    public List<CheckInPersonBO> getCheckInPersonById(Integer id){
        return checkInPersonDAO.getCheckInPersonById(id);
    }

    //检查身份证号是否在住
    public Integer checkId(String certificateNumber,Integer orderId){
        return checkInPersonDAO.checkId(certificateNumber,orderId);
    }

}

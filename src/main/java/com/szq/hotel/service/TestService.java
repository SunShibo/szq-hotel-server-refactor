package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
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
@Service("testService")
@Transactional
public class TestService {
    @Resource
    private TestDAO testDAO;

    @Resource
    CheckInPersonDAO checkInPersonDAO;

   public void  test() {
       testDAO.updateU1(1,100);
       //模拟出现异常
       int a=5/0;
       testDAO.updateU2(2,100);
    }

    public List<CheckInPersonBO> getCheckInPersonById(Integer orderChildId){
        return checkInPersonDAO.getCheckInPersonById(orderChildId, Constants.CHECKIN.getValue());
    }
}

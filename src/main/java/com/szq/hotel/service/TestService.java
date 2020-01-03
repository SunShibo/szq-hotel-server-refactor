package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.CheckInPersonDAO;
import com.szq.hotel.dao.ManagerDailyDAO;
import com.szq.hotel.dao.TestDAO;
import com.szq.hotel.entity.bo.CheckInPersonBO;
import com.szq.hotel.entity.bo.OrderReBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Created by Administrator on 2018/2/28.
 */
@Service("testService")
@Transactional
public class TestService {
    @Resource
    private TestDAO testDAO;

    @Resource
    ManagerDailyDAO managerDailyDAO;

   public  void  test() {
            for(int i=0;i<91;i++){
                Calendar start = Calendar.getInstance();
                start.set(Calendar.MONTH, 8);
                start.set(Calendar.DAY_OF_MONTH, 18);
                start.set(Calendar.HOUR_OF_DAY, 04);
                start.set(Calendar.MINUTE, 0);
                start.set(Calendar.SECOND, 0);
                start.add(Calendar.DAY_OF_MONTH, i);

                Calendar end = Calendar.getInstance();
                end.set(Calendar.MONTH, 8);
                end.set(Calendar.DAY_OF_MONTH, 19);
                end.set(Calendar.HOUR_OF_DAY, 04);
                end.set(Calendar.MINUTE, 0);
                end.set(Calendar.SECOND, 0);
                end.add(Calendar.DAY_OF_MONTH, i);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("start:"+simpleDateFormat.format(start.getTime()));
                System.out.println("end:"+simpleDateFormat.format(end.getTime()));


                List<OrderReBO> orderReBOS = managerDailyDAO.queryOrderRe(1, simpleDateFormat.format(start.getTime()), simpleDateFormat.format(end.getTime()));
                BigDecimal day=new BigDecimal("0");
                if(orderReBOS!=null && orderReBOS.size()>0){
                    for(OrderReBO or:orderReBOS){
                        if(or.getMoney()!=null) {
                            day=day.add(or.getMoney());
                        }
                    }
                }

                BigDecimal hour = managerDailyDAO.queryhourRate(1, simpleDateFormat.format(start.getTime()), simpleDateFormat.format(end.getTime()));
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println(simpleDateFormat1.format(start.getTime()));
                managerDailyDAO.up(day.abs(),hour.abs(),simpleDateFormat1.format(start.getTime()));

            }
    }


}

package com.szq.hotel.job;

import com.szq.hotel.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component("checkOrderState")
public class CheckOrderState {

    @Resource
    OrderService orderService;

    int count=0;
    @Scheduled(cron="0 0/5 * * * *")    // 13.15 启动项目
    public void CheckOrderState(){
        count++;
        try {
            orderService.closeOrder();
            System.err.println("执行"+count+"次当前时间:"+new Date());
        } catch (Exception e) {
            System.out.println("修改失败");
        }
    }

    /**
     * 每天4点滚房费
     */
    @Scheduled(cron="0 0 4 * * *")    // 13.15 启动项目
    public void nightAuditor(){
        //查询所有子订单  在住中的...

            //判断6点来的还是6点后来的
                    //查出所有符合日期之前的没有滚过的房费

            //查出没有滚过房费的房费

      //  滚动房费生成记录 生成报表
    }





}
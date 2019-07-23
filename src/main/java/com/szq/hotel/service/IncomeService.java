package com.szq.hotel.service;

import com.szq.hotel.dao.IncomeDAO;
import com.szq.hotel.entity.bo.IncomeBO;
import com.szq.hotel.util.DateUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IncomeService {

    @Resource
    IncomeDAO incomeDAO;

    //获取营收入报表
    public List<IncomeBO> getIncome(Integer hotelId){
        //获取今天日期
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour<=6){
            calendar.add(Calendar.DATE, -1);
        }
        int year = calendar.get(Calendar.YEAR);//获取年份

        int month=calendar.get(Calendar.MONTH);//获取月份

        int day=calendar.get(Calendar.DATE);//获取日

        //获取当日营收
        IncomeBO incomeBO=incomeDAO.getIncome(year,month,day,hotelId);

        //获取当月营收
        IncomeBO incomeBO1=incomeDAO.getIncome(year,month,null,hotelId);

        //获取上月营收
        IncomeBO incomeBO2=incomeDAO.getIncome(year,month-1,null,hotelId);

        //获取当年营收
        IncomeBO incomeBO3=incomeDAO.getIncome(year,null,null,hotelId);

        //获取去年同月营收
        IncomeBO incomeBO4=incomeDAO.getIncome(year-1,month,null,hotelId);

        //获取去年营收
        IncomeBO incomeBO5=incomeDAO.getIncome(year-1,null,null,hotelId);

        //月差异
        Double monthDifferences;//月差异
        Double yearDifferences;//年差异

        //差异 用当月减去上月 除以上月

        //前台算 还是后台算 返回格式
        return null;
    }

    //添加营收记录
    public void addIncome(IncomeBO incomeBO){
        incomeDAO.addIncome(incomeBO);
    }
}

package com.szq.hotel.service;

import com.szq.hotel.dao.ManagementReportDAO;
import com.szq.hotel.entity.bo.ManagementReportBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service("managementReportService")
@Transactional
public class ManagementReportService {
    @Resource
    private ManagementReportDAO managementReportDAO;
    /*
        添加数据
     */
    public void addData(ManagementReportBO managementReportBO){


        managementReportDAO.addData(managementReportBO);
    }
    //查询应收合计（管理层报表）
    public BigDecimal getReceivableSum(Map<String,Object> map){
        return managementReportDAO.getReceivableSum(map);
    }
    //房费收入
    public BigDecimal getRoomRate(){
        return managementReportDAO.getRoomRate();
    }
    //房间总数
    public Integer getRoomSum(Map<String,Object> map){
        return managementReportDAO.getRoomSum(map);
    }
    //会员卡收入
    public BigDecimal getMemberCardRate(){
        return managementReportDAO.getMemberCardRate();
    }
    //入住人数
    public Integer getCheckInPerson(Map<String,Object> map){
        return managementReportDAO.getCheckInPerson(map);
    }
    //赔偿收入
    public BigDecimal getCompensation(){
        return managementReportDAO.getCompensation();
    }
    //免费入住房数
    public Integer getFreeRoomSum(Map<String,Object> map){
        return managementReportDAO.getFreeRoomSum(map);
    }
    //维修房数
    public Integer getMaintainSum(){
        return managementReportDAO.getMaintainSum();
    }
    //会员房数
    public Integer getMemberRoomSum(Map<String,Object> map){
        return managementReportDAO.getMemberRoomSum(map);
    }
    //商品收入
    public BigDecimal getCommodity(){
        return managementReportDAO.getCommodity();
    }
    //房费调整
    public BigDecimal getRoomRateAdjustment(){
        return managementReportDAO.getRoomRateAdjustment();
    }
    //出租率
    //REVPAR REVPAR = 应收合计 / (总房间数 - 维修房数)
    public BigDecimal REVPAR(Map<String,Object> map){
        //应收合计
        BigDecimal receivableSum = managementReportDAO.getReceivableSum(map);
        //总房间数
        Integer roomSum = managementReportDAO.getRoomSum(map);
        //维修房数
        Integer maintainSum = managementReportDAO.getMaintainSum();
        BigDecimal REVPAR = receivableSum.divide(new BigDecimal(roomSum-maintainSum));
        return REVPAR;
    }
    //锁房数
    public Integer getLockRoomSum(){
        return managementReportDAO.getLockRoomSum();
    }
    //停用房间数 =  维修房数+锁房数
    public Integer getDisableRoomSum(){
        //维修房数
        Integer maintainSum = managementReportDAO.getMaintainSum();
        //锁房数
        Integer lockRoomSum = managementReportDAO.getLockRoomSum();
        //停用房间数
        Integer disableRoomSum = maintainSum + lockRoomSum;
        return disableRoomSum;
    }
    //空房数
    public Integer getEmptyRoomSum(){
        return managementReportDAO.getEmptyRoomSum();
    }

    //钟点房晚数
    public Integer getHourRoomSum(Map<String,Object> map){
        return managementReportDAO.getHourRoomSum(map);
    }

}

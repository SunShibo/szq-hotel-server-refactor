package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.OrderRecordDAO;
import com.szq.hotel.entity.bo.ChildOrderBO;
import com.szq.hotel.entity.bo.OrderRecoredBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 *  子订单详情
 */
@Service("OrderRecordService")
@Transactional
public class OrderRecordService {
    private static final Logger log = LoggerFactory.getLogger(OrderRecordService.class);

    @Resource
    private OrderRecordDAO orderRecordDAO;
    @Resource
    private ChildOrderService childOrderService;

    /**
     * 查询子订单详情
     */
    public List<OrderRecoredBO>  queryOrderRecord(Integer orderChildId){
        return orderRecordDAO.queryOrderRecord(orderChildId);
    }

    /**
     * 添加子订单详情
     * @param orderChildId 子订单id
     * @param info     详情
     * @param payType  支付方式
     * @param money   金额
     * @param project  项目
     * @param userId  用户id
     * @param number  数量
     */
    //FIXME 生成记录应在主订单中
    public void addOrderRecord(Integer orderChildId , String info, String payType, BigDecimal money,String project,Integer userId,String number){
        log.info("star addOrderRecord..................................");
        log.info("orderChildId:{}\tinfo:{}\tpayType:{}\tmoney:{}\tproject:{}\tuserId:{}\tnumber:{}",
                orderChildId,info,payType,money,project,userId,number);

        //查询子订单
        ChildOrderBO childOrderBO = childOrderService.queryOrderChildById(orderChildId);
        OrderRecoredBO orderRecoredBO=new OrderRecoredBO();
        orderRecoredBO.setCreateUserId(userId);
        //此间房就是主账房
        if(Constants.YES.getValue().equals(childOrderBO.getMain())) {
            orderRecoredBO.setOrderChildId(orderChildId);
            orderRecoredBO.setInfo(info);
        }else{
            //查询主账房
            Integer mainId=childOrderService.queryOrderChildMain(childOrderBO.getAlRoomCode());
            orderRecoredBO.setOrderChildId(mainId);
            orderRecoredBO.setInfo(info+"("+  childOrderBO.getRoomName()+"转入)");
        }
        orderRecoredBO.setPayType(payType);
        orderRecoredBO.setMoney(money);
        orderRecoredBO.setProject(project);
        orderRecoredBO.setNumber(number);
        orderRecoredBO.setState(Constants.NO.getValue());
        orderRecordDAO.addOrderRecord(orderRecoredBO);
        log.info("end addOrderRecord..................................");
    }



}
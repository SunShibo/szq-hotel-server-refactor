package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.OrderRecordDAO;
import com.szq.hotel.entity.bo.ChildOrderBO;
import com.szq.hotel.entity.bo.OrderRecoredBO;
import com.szq.hotel.util.StringUtils;
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


    /**
     * 查询子订单详情
     */
    public OrderRecoredBO queryOrderRecordById(Integer id){
        return orderRecordDAO.queryOrderRecordById(id);
    }

    /**
     * 修改子订单详情
     */
    public void updateRecord(OrderRecoredBO orderRecoredBO){
        log.info("star updateRecord.......................................................");
        log.info("orderRecoredBO:{}",orderRecoredBO);
        //查询子订单
        ChildOrderBO childOrderBO = childOrderService.queryOrderChildById(orderRecoredBO.getOrderChildId());
        //此间房不是主账房
        if(!Constants.YES.getValue().equals(childOrderBO.getMain())) {
            log.info("updateRecord........................................................");
            log.info("orderRecoredBO:{}",orderRecoredBO);
            //查询主账房
            Integer mainId=childOrderService.queryOrderChildMain(childOrderBO.getAlRoomCode());
            orderRecoredBO.setOrderChildId(mainId);
            orderRecoredBO.setInfo(orderRecoredBO.getInfo()+"("+  childOrderBO.getRoomName()+"转入)");
            log.info("orderRecoredBO:{}",orderRecoredBO);
        }

        orderRecordDAO.updateRecord(orderRecoredBO);
        log.info("end updateRecord.......................................................");
    }

    /**
     * 查询是否包含以结账
     */
    public boolean queryInvoicing(String ids){
        List<Integer> list = StringUtils.strToList(ids);
        return orderRecordDAO.queryInvoicing(list)>0;
    }


    /**
     * 查询支付方式
     * @param list
     * @return
     */
    public List<String> queryPayType(List<Integer> list) {
        return  orderRecordDAO.queryPayType(list);
    }

    /**
     * 查询消费金额
     * @param list
     * @return
     */
   public double consumption(List<Integer> list) {
        return  orderRecordDAO.consumption(list);
    }

    /**
     * 查询支付多少金额
     * @param list
     * @return
     */
    public double pay(List<Integer> list) {
        return  orderRecordDAO.pay(list);
    }

    /**
     * 修改子订单为已结
     * @param list
     */
    public void closedAccount(List<Integer> list) {
        orderRecordDAO.closedAccount(list);
    }


    public List<Integer> queryRecordIds(List<Integer> list) {
        return orderRecordDAO.queryRecordIds(list);
    }

    /**
     * 修改订单为已经结账
     */
    public void completeAccount(List<Integer> list){
        orderRecordDAO.completeAccount(list);
    }
}
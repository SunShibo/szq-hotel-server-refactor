package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.ChildOrderDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.util.IDBuilder;
import com.szq.hotel.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ChildOrderService")
@Transactional
public class ChildOrderService {
    private static final Logger log = LoggerFactory.getLogger(ChildOrderService.class);
    @Resource
    private ChildOrderDAO childOrderDAO;
    @Resource
    private OrderRecordService orderRecordService;
    @Resource
    private CashierSummaryService cashierSummaryService;


    /**
     * 押金
     * @param payType
     * @param orderChildId
     * @param money
     */
    public void addCashPledge(String payType, Integer orderChildId, BigDecimal money,Integer userId,Integer hotelId) {
        log.info("start addCashPledge........................................");
        log.info("payType:{}\torderChildId:{}\tmoney:{}\tuserId:{}",payType,orderChildId,money,userId);
        //生成记录
        orderRecordService.addOrderRecord(orderChildId,"入住押金",payType,money,Constants.CASHPLEDGE.getValue(),userId,null);
        //增加金额
        if(Constants.CASH.getValue().equals(payType)){
            log.info("increaseCashCashPledge...............................");
            childOrderDAO.increaseCashCashPledge(orderChildId,money);
        }else{
            log.info("increaseOtherCashPledge...............................");
            childOrderDAO.increaseOtherCashPledge(orderChildId,money);
        }

        ChildOrderBO order = childOrderDAO.queryOrderChildById(orderChildId);
        //报表 FIXME 小汶 未完成
        cashierSummaryService.addCheck(money,payType,IDBuilder.getOrderNumber(),userId,order.getName(), order.getOTA(),
                order.getChannel(),order.getPassengerSource(),order.getRoomName(),order.getRoomTypeName(),null, hotelId);

        log.info("end  addCashPledge........................................");
    }


    /**
     * 入账
     * @param orderChildId 子订单id
     * @param money  金额
     * @param designation  费用名称
     * @param type  消费类型
     * @param userId   用户id
     * @param hotelId  酒店id
     */
    public void recorded(Integer orderChildId, BigDecimal money, String designation, String type, Integer userId, Integer hotelId) {
        log.info("start recorded........................................");
        log.info("orderChildId:{}\tmoney:{}\tdesignation:{}\ttype:{}\tuserId:{}\thotelId:{}",orderChildId,money,designation,type,userId,hotelId);
        //生成记录
        orderRecordService.addOrderRecord(orderChildId,designation,null,new BigDecimal("-1").multiply(money),type,userId,null);
        if(type.equals(Constants.ROOMRATE.getValue())){
            log.info("increaseRoomRate...............................");
            childOrderDAO.increaseRoomRate(orderChildId,money);
        }else{
            log.info("increaseOtherRate...............................");
            childOrderDAO.increaseOtherRate(orderChildId,money);
        }

        ChildOrderBO order = childOrderDAO.queryOrderChildById(orderChildId);
        //报表 FIXME 小汶 未完成
        cashierSummaryService.addAccount(type,money,order.getOrderNumer(),userId,order.getName(),order.getOTA(),order.getChannel(),
                order.getPassengerSource(),order.getRoomName(),order.getRoomTypeName(),designation,hotelId);
        log.info("end  recorded........................................");

    }

    /**
     * 查询挂账信息
     * @param roomName
     * @return
     */
    public List<BuyerBuyingBO> querySuspend(String roomName) {
        return childOrderDAO.querySuspend(roomName);
    }

    /**
     * 冲减
     * @param orderChildId
     * @param money
     * @param remark
     * @param hotelId
     */
    public void free(Integer orderChildId, BigDecimal money, String remark, Integer userId, Integer hotelId) {
        log.info("start free........................................");
        log.info("orderChildId:{}\tmoney:{}\tremark:{}\tuserId:{}\thotelId:{}",orderChildId,money,remark,userId,hotelId);
        //生成记录
        orderRecordService.addOrderRecord(orderChildId,remark,null,money,Constants.FREEORDER.getValue(),userId,null);
        log.info("free...................................");
        //变为负数
        BigDecimal negation=money.multiply(new BigDecimal("-1"));

        childOrderDAO.free(orderChildId,negation);

        ChildOrderBO order = childOrderDAO.queryOrderChildById(orderChildId);
        //报表 FIXME 小汶 未完成
        cashierSummaryService.addFree(negation,order.getOrderNumer(),userId,order.getName(),order.getOTA(),order.getChannel(),
                order.getPassengerSource(),order.getRoomName(),order.getRoomTypeName(),remark,hotelId);
        log.info("end  free..........................................");
    }

    /**
     * 查询子订单
     * @param childId
     * @return
     */
    public ChildOrderBO queryOrderChildById(Integer childId){
        return childOrderDAO.queryOrderChildById(childId);
    }

    /**
     * 通过联房码查询主账房
     */
    public Integer queryOrderChildMain(String code){
        log.info("queryOrderChildMain.......................................................");
        return  childOrderDAO.queryOrderChildMain(code);
    }

    /**
     *
     * @param ids     详情id
     * @param shiftToId  转入id
     * @param rollOutId  转出id
     */
    public void transferAccounts(Integer userId,String ids, Integer shiftToId, Integer rollOutId) {
        log.info("start transferAccounts.........................................................");
        log.info("userId:{}\tids:{}\tshiftToId:{}\trollOutId:{}",userId,ids,shiftToId,rollOutId);
        String[] split = ids.split(",");
        for(int i=0;i<split.length;i++){
            OrderRecoredBO orderRecoredBO = orderRecordService.queryOrderRecordById(split.length);
            //押金
            if(Constants.CASHPLEDGE.getValue().equals(orderRecoredBO.getProject())){
                log.info("start transferAccounts....CASHPLEDGE.....................................................");
                //现金
                if(Constants.CASH.getValue().equals(  orderRecoredBO.getPayType())){
                    log.info("start transferAccounts....CASH.....................................................");
                    childOrderDAO.increaseCashCashPledge(shiftToId,orderRecoredBO.getMoney());
                    childOrderDAO.reduceCashCashPledge(rollOutId,orderRecoredBO.getMoney());
                }else {
                    //非现金
                    log.info("start transferAccounts....OtherCashPledge.....................................................");
                    childOrderDAO.increaseOtherCashPledge(shiftToId,orderRecoredBO.getMoney());
                    childOrderDAO.reduceOtherCashPledge(rollOutId,orderRecoredBO.getMoney());
                }

            }else if (Constants.ROOMRATE.getValue().equals(orderRecoredBO.getProject())){
                //房费
                log.info("start transferAccounts....ROOMRATE.....................................................");
                childOrderDAO.increaseRoomRate(shiftToId,orderRecoredBO.getMoney());
                childOrderDAO.reduceRoomRate(rollOutId,orderRecoredBO.getMoney());

            }else if (Constants.COMMODITY.getValue().equals(orderRecoredBO.getProject())|| Constants.COMPENSATE.getValue().equals(orderRecoredBO.getProject())
                    ||Constants.TIMEOUTCOST.getValue().equals(orderRecoredBO.getProject())){
                //商品 赔偿 超时费
                log.info("start transferAccounts....OtherRate.....................................................");
                childOrderDAO.increaseOtherRate(shiftToId,orderRecoredBO.getMoney());
                childOrderDAO.reduceOtherRate(rollOutId,orderRecoredBO.getMoney());

            }else if (Constants.FREEORDER.getValue().equals(orderRecoredBO.getProject())||Constants.MITIGATE.getValue().equals(orderRecoredBO.getProject())){
                log.info("start transferAccounts....free.....................................................");
                //免单 超时费减免
                childOrderDAO.free(shiftToId,orderRecoredBO.getMoney());
                childOrderDAO.reducefree(rollOutId,orderRecoredBO.getMoney());
            }

            ChildOrderBO childOrderBO = childOrderDAO.queryOrderChildById(rollOutId);
            orderRecoredBO.setInfo(orderRecoredBO.getInfo()+"("+childOrderBO.getRoomName()+"转入)");
            orderRecoredBO.setOrderChildId(shiftToId);
            orderRecoredBO.setCreateUserId(userId);
            orderRecordService.updateRecord(orderRecoredBO);

        }

        log.info("end transferAccounts.........................................................");
    }

    /**
     * 单项结账查询
     */
    public Map<String,Object> queryChildleAccounts(String ids) {
        log.info("start queryChildleAccounts.........................................................");
        log.info("ids:{}",ids);
        List<Integer> list = StringUtils.strToList(ids);
        List<String>  payType=orderRecordService.queryPayType(list);
        //查询消费多少
        double consumption=orderRecordService.consumption(list);
        //查询交了多少
        double pay=orderRecordService.pay(list);

        Map<String,Object>  resultMap=new HashMap<String, Object>();
        return null;
    }


}
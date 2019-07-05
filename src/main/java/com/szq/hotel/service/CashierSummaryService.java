package com.szq.hotel.service;

import com.szq.hotel.dao.CashierSummaryDAO;
import com.szq.hotel.entity.bo.CashierSummaryBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 *  收银汇总
 */
@Service("CashierSummaryService")
@Transactional
public class CashierSummaryService {
    private static final Logger log = LoggerFactory.getLogger(CashierSummaryService.class);

    @Resource
    private CashierSummaryDAO cashierSummaryDAO;

    /**
     * 商品交易
     * @param payType 支付方式
     * @param money   金额
     * @param info    详细信息
     * @param type    消费类型 商品/赔偿
     * @param orderNumber   单号
     * @param userName   操作员名字
     */
    public void addCommodity(String payType, BigDecimal money,String info,String type,String orderNumber,String userName){
        log.info("start addCommodity...........................................");
        log.info("payType:{}\tmoney:{}\tinfo:{}\ttype:{}\torderNumber:{}\tuserName:{}",payType,money,info,type,orderNumber,userName);
        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(type);
        cashierSummaryBO.setType(payType);
        cashierSummaryBO.setRemark(info);
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setOperator(userName);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end  addCommodity...........................................");
    }


    /**
     * 办卡
     * @param name 姓名
     * @param money  金额
     * @param payType 支付方式
     * @param orderNumber 订单号
     * @param userName   操作员名字
     */
    public void addCard(String name, BigDecimal money,String payType,String orderNumber,String userName) {
        log.info("start addCard...........................................");
        log.info("name:{}\tmoney:{}\tpayType:{}\torderNumber:{}\tuserName:{}",name,money,payType,orderNumber,userName);
        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject("办卡"); //TODO  应该定义为常量
        cashierSummaryBO.setType(payType);
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setOperator(userName);
        cashierSummaryBO.setName(name);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addCard...........................................");
    }


    /**
     *  储值
     * @param name 姓名
     * @param money 金额
     * @param payType 支付方式
     * @param orderNumber 订单号
     * @param userName    操作员名字
     */
    public void addStored(String name, BigDecimal money,String payType,String orderNumber,String userName) {
        log.info("start addStored...........................................");
        log.info("name:{}\tmoney:{}\tpayType:{}\torderNumber:{}\tuserName:{}",name,money,payType,orderNumber,userName);
        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject("储值"); //TODO  应该定义为常量
        cashierSummaryBO.setType(payType);
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setOperator(userName);
        cashierSummaryBO.setName(name);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addStored...........................................");
    }


    /**
     *  入住 / 客账押金
     * @param money     金额
     * @param payType   支付方式
     * @param orderNumber  订单号
     * @param userName     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param remark           备注
     */
    public void addCheck(BigDecimal money,String payType,String orderNumber,String userName,String name,String OTA,
                         String channel,String passengerSource,String roomName,String roomType,String remark) {
        log.info("start addCheck...........................................");
        log.info("money:{}\tpayType:{}\torderNumber:{}\tuserName:{}\tname:{}\tOTA:{}\tchannel:{}\tpassengerSource:{}\troomName:{}\troomType:{}\tremark:{}",
               money,payType,orderNumber,userName,name,OTA,channel,passengerSource,roomName,roomType,remark);
        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject("押金"); //TODO  应该定义为常量
        cashierSummaryBO.setType(payType);
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setOperator(userName);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setRemark(remark);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addCheck...........................................");
    }


    /**
     *  入账
     * @Param project 消费类型 商品,赔偿,房费
     * @param money     金额
     * @param orderNumber  订单号
     * @param userName     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param designation      费用名称
     */
    public void addAccount(String project,BigDecimal money,String orderNumber,String userName,String name,String OTA,
                         String channel,String passengerSource,String roomName,String roomType,String designation) {
        log.info("start addAccount...........................................");
        log.info("project:{}\tmoney:{}\torderNumber:{}\tuserName:{}\tname:{}\tOTA:{}\tchannel:{}\tpassengerSource:{}\troomName:{}\troomType:{}\tdesignation:{}",
                project,money,orderNumber,userName,name,OTA,channel,passengerSource,roomName,roomType,designation);

                CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(project);
        cashierSummaryBO.setConsumption(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setOperator(userName);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setRemark(designation);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("start addAccount...........................................");

    }



    /**
     *  房费 (夜审|退房)
     * @param money     金额
     * @param orderNumber  订单号
     * @param userName     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param remark          备注
     */
    public void addRoomRate(BigDecimal money,String orderNumber,String userName,String name,String OTA,
                           String channel,String passengerSource,String roomName,String roomType,String remark) {

        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject("房费"); //TODO 定义常量
        cashierSummaryBO.setConsumption(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setOperator(userName);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setRemark(remark);
        cashierSummaryDAO.addData(cashierSummaryBO);

    }

    /**
     * 房费调整 (超时费减免 | 冲账) 金额为正数
     * @param money     金额
     * @param orderNumber  订单号
     * @param userName     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param cause            原因
     */
    public void addFree(BigDecimal money,String orderNumber,String userName,String name,String OTA,
                            String channel,String passengerSource,String roomName,String roomType,String cause) {
        log.info("start addFree...........................................");
        log.info("money:{}\torderNumber:{}\tuserName:{}\tname:{}\tOTA:{}\tchannel:{}\tpassengerSource:{}\troomName:{}\troomType:{}\tcause:{}",
                 money,orderNumber,userName,name,OTA,channel,passengerSource,roomName,roomType,cause);

        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject("房费调整"); //TODO 定义常量
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setOperator(userName);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setRemark(cause);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addFree...........................................");
    }


    /**
     * 结账  金额为正负数
     * @param money     金额
     * @param orderNumber  订单号
     * @param userName     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param remark            原因
     */
    public void addAccounts(BigDecimal money,String orderNumber,String userName,String name,String OTA,
                        String channel,String passengerSource,String roomName,String roomType,String remark) {
        log.info("start addAccounts...........................................");
        log.info("money:{}\torderNumber:{}\tuserName:{}\tname:{}\tOTA:{}\tchannel:{}\tpassengerSource:{}\troomName:{}\troomType:{}\tremark:{}",
                money,orderNumber,userName,name,OTA,channel,passengerSource,roomName,roomType,remark);

        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject("结账"); //TODO 定义常量
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setOperator(userName);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setRemark(remark);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addAccounts...........................................");
    }


    /**
     * 收银总会查询
     */
     public List<CashierSummaryBO>  queryCashierSummary(CashierSummaryBO cashierSummaryBO){
        return  cashierSummaryDAO.queryCashierSummary(cashierSummaryBO);
     }


}
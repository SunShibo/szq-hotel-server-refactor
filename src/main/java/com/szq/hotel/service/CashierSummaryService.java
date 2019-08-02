package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
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
     * @param userId   操作员名字
     * @param hotelId            酒店id
     */
    public void addCommodity(String payType, BigDecimal money,String info,String type,String orderNumber,Integer  userId,Integer hotelId){
        log.info("start addCommodity...........................................");
        log.info("payType:{}\tmoney:{}\tinfo:{}\ttype:{}\torderNumber:{}\tuserId:{}\thotelId:{}",payType,money,info,type,orderNumber,userId,hotelId);
        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(type);
        cashierSummaryBO.setType(payType);
        cashierSummaryBO.setRemark(info);
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setUserId(userId);
        cashierSummaryBO.setHotelId(hotelId);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end  addCommodity...........................................");
    }


    /**
     * 办卡
     * @param name 姓名
     * @param money  金额
     * @param payType 支付方式
     * @param orderNumber 订单号
     * @param userId   操作员名字
     * @param hotelId            酒店id
     */
    public void addCard(String name, BigDecimal money,String payType,String orderNumber,Integer userId,Integer hotelId) {
        log.info("start addCard...........................................");
        log.info("name:{}\tmoney:{}\tpayType:{}\torderNumber:{}\tuserId:{}\thotelId:{}",name,money,payType,orderNumber,userId,hotelId);
        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(Constants.APPLYCARD.getValue());
        cashierSummaryBO.setType(payType);
        cashierSummaryBO.setConsumption(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setUserId(userId);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setHotelId(hotelId);
        cashierSummaryDAO.addData(cashierSummaryBO);
        cashierSummaryBO.setConsumption(new BigDecimal("0"));
        cashierSummaryBO.setSettlement(money);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addCard...........................................");
    }


    /**
     *  储值
     * @param name 姓名
     * @param money 金额
     * @param payType 支付方式
     * @param orderNumber 订单号
     * @param userId    操作员名字
     * @param hotelId            酒店id
     */
    public void addStored(String name, BigDecimal money,String payType,String orderNumber,Integer userId,Integer hotelId) {
        log.info("start addStored...........................................");
        log.info("name:{}\tmoney:{}\tpayType:{}\torderNumber:{}\tuserId:{}\thotelId:{}",name,money,payType,orderNumber,userId,hotelId);
        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(Constants.STOREDVALUE.getValue());
        cashierSummaryBO.setType(payType);
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setUserId(userId);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setHotelId(hotelId);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addStored...........................................");
    }


    /**
     *  入住 / 客账押金
     * @param money     金额
     * @param payType   支付方式
     * @param orderNumber  订单号
     * @param userId     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param remark           备注
     * @param hotelId            酒店id
     */
    public void addCheck(BigDecimal money,String payType,String orderNumber,Integer userId,String name,String OTA,
                         String channel,String passengerSource,String roomName,String roomType,String remark,Integer hotelId) {
        log.info("start addCheck...........................................");
        log.info("money:{}\tpayType:{}\torderNumber:{}\tuserId:{}\tname:{}\tOTA:{}\tchannel:{}\tpassengerSource:{}\troomName:{}\troomType:{}\tremark:{}\thotelId{}",
               money,payType,orderNumber,userId,name,OTA,channel,passengerSource,roomName,roomType,remark,hotelId);
        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(Constants.CASHPLEDGE.getValue());
        cashierSummaryBO.setType(payType);
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setUserId(userId);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setRemark(remark);
        cashierSummaryBO.setHotelId(hotelId);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addCheck...........................................");
    }


    /**
     *  入账
     * @Param project 消费类型 商品,赔偿,房费
     * @param money     金额
     * @param orderNumber  订单号
     * @param userId     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param hotelId            酒店id
     * @param designation      费用名称
     */
    public void addAccount(String project,BigDecimal money,String orderNumber,Integer userId,String name,String OTA,
                         String channel,String passengerSource,String roomName,String roomType,String designation,Integer hotelId) {
        log.info("start addAccount...........................................");
        log.info("project:{}\tmoney:{}\torderNumber:{}\tuserId:{}\tname:{}\tOTA:{}\tchannel:{}\tpassengerSource:{}\troomName:{}\troomType:{}\tdesignation:{}\thotelId:{}",
                project,money,orderNumber,userId,name,OTA,channel,passengerSource,roomName,roomType,designation,hotelId);

        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(project);
        cashierSummaryBO.setConsumption(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setUserId(userId);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setRemark(designation);
        cashierSummaryBO.setHotelId(hotelId);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("start addAccount...........................................");

    }



    /**
     *  房费 (夜审|退房)
     * @param money     金额
     * @param orderNumber  订单号
     * @param userId     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param hotelId            酒店id
     */
    public void addRoomRate(BigDecimal money,String orderNumber,Integer userId,String name,String OTA,
                           String channel,String passengerSource,String roomName,String roomType,Integer hotelId) {
        log.info("start addRoomRate...........................................");
        log.info("money:{}\torderNumber:{}\tuserId:{}\tname:{}\tOTA:{}\tchannel:{}\tpassengerSource:{}\troomName:{}\troomType:{}\tdesignation:{}\thotelId:{}",
                money,orderNumber,userId,name,OTA,channel,passengerSource,roomName,roomType,hotelId);
                CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(Constants.ROOMRATE.getValue());
        cashierSummaryBO.setConsumption(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setUserId(userId);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setHotelId(hotelId);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addRoomRate...........................................");
    }

    /**
     * 房费调整 (超时费减免 | 冲账) 金额为负数
     * @param money     金额
     * @param orderNumber  订单号
     * @param userId     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param cause            原因
     * @param hotelId            酒店id
     */
    public void addFree(BigDecimal money,String orderNumber,Integer userId,String name,String OTA,String channel,
                        String passengerSource,String roomName,String roomType,String cause,Integer hotelId,String project) {
        log.info("start addFree...........................................");
        log.info("money:{}\torderNumber:{}\tuserId:{}\tname:{}\tOTA:{}\tchannel:{}\tpassengerSource:{}\troomName:{}\troomType:{}\tcause:{}\thotelId:{}",
                 money,orderNumber,userId,name,OTA,channel,passengerSource,roomName,roomType,cause,hotelId);

        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(project);
        cashierSummaryBO.setConsumption(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setUserId(userId);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setRemark(cause);
        cashierSummaryBO.setHotelId(hotelId);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addFree...........................................");
    }


    /**
     * 结账  金额为正负数
     * @param money     金额
     * @param orderNumber  订单号
     * @param userId     操作人id
     * @param name         入住人名字(主)
     * @param OTA          OTA
     * @param channel      渠道
     * @param passengerSource  客源
     * @param roomName         房间名字
     * @param roomType         房间类型
     * @param remark            原因
     * @param hotelId            酒店id
     * @param type               支付方式
     */
    public void addAccounts(BigDecimal money,String orderNumber,Integer userId,String name,String OTA,String type,
                        String channel,String passengerSource,String roomName,String roomType,String remark,Integer hotelId) {
        log.info("start addAccounts...........................................");
        log.info("money:{}\torderNumber:{}\tuserId:{}\tname:{}\tOTA:{}\tchannel:{}\tpassengerSource:{}\troomName:{}\troomType:{}\tremark:{}\thotelId:{}\ttype:{}",
                money,orderNumber,userId,name,OTA,channel,passengerSource,roomName,roomType,remark,hotelId,type);

        CashierSummaryBO  cashierSummaryBO=new CashierSummaryBO();
        cashierSummaryBO.setProject(Constants.SETTLE.getValue());
        cashierSummaryBO.setSettlement(money);
        cashierSummaryBO.setOrderNumber(orderNumber);
        cashierSummaryBO.setUserId(userId);
        cashierSummaryBO.setName(name);
        cashierSummaryBO.setOTA(OTA);
        cashierSummaryBO.setChannel(channel);
        cashierSummaryBO.setPassengerSource(passengerSource);
        cashierSummaryBO.setRoomName(roomName);
        cashierSummaryBO.setRoomTypeName(roomType);
        cashierSummaryBO.setRemark(remark);
        cashierSummaryBO.setHotelId(hotelId);
        cashierSummaryBO.setType(type);
        cashierSummaryDAO.addData(cashierSummaryBO);
        log.info("end addAccounts...........................................");
    }


    /**
     * 收银总会查询
     */
     public List<CashierSummaryBO>  queryCashierSummary(CashierSummaryBO cashierSummaryBO){
        return  cashierSummaryDAO.queryCashierSummary(cashierSummaryBO);
     }


    /**
     * 删除收银汇总
     */
    public void deleteCashierSummary(Integer id){
        cashierSummaryDAO.deleteCashierSummary(id);
    }
}
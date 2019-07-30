package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.param.OrderChildBackupParam;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.entity.result.CheckInInfoResult;
import com.szq.hotel.entity.result.CheckRoomPersonResult;
import com.szq.hotel.entity.result.OrderResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderDAO {
    //添加主订单 返回id
    Integer addOrder(OrderBO orderBO);
    //添加子订单 返回id
    Integer addOrderChild(OrderChildBO orderChildBO);
    //修改子订单
    Integer updOrderChild(OrderChildBO orderChildBO);
    //修改子订单支付时间
    Integer updOrderChildUpdateTime(Integer id);
    //修改子订单
    Integer updOrderChildMoney(OrderChildBO orderChildBO);
    //根据手机号 身份证号 查询主订单预约信息
    OrderBO getOrderByIdOrMobile(@Param("idNumber")String idNumber, @Param("mobile")String mobile, @Param("date") String date,@Param("hotelId")Integer hotelId);
    //根据订单id查询订单信息
    OrderBO getOrderById(Integer orderId);
    //根据订单id查询子订单 带房型房间信息
    List<OrderChildBO> getOrderChildByOrderId(Integer id);
    //根据订单id查询预约中的子订单 带房型信息
    List<OrderChildBO> getOrderChildByOrderId2(@Param("id") Integer id,@Param("orderState") String orderState);
    //根据订单id查询预约中的子订单 带房型房间信息
    List<OrderChildBO> getOrderChildByOrderId3(@Param("id") Integer id,@Param("orderState") String orderState);
    //根据订单id查询没有入住的子订单
    List<OrderChildBO> getOrderChildByOrderId4(@Param("id") Integer id,@Param("orderState") String orderState);
    //根据订单id查询所有已入住 状态为预约中的子订单
    List<OrderChildBO> getOrderChildByOrderId5(@Param("id") Integer id,@Param("orderState") String orderState);
    //根据子订单id查询子订单
    OrderChildBO getOrderChildById(Integer id);
    //根据子订单id查询子订单
    OrderChildBO getOrderChildById2(Integer id);
    //根据房间id查询正在预约中的子订单
    OrderChildBO getResOrderChildByRoomId(@Param("roomId") Integer roomId,@Param("orderId")Integer orderId);
    //根据房型id查询正在预约中的子订单
    OrderChildBO getResOrderChildByRoomTypeId(@Param("roomTypeId")Integer roomTypeId,@Param("orderId")Integer orderId);
    //修改主订单
    Integer updOrder(OrderBO orderBO);
    //根据主订单查询预约中的子订单
    List<OrderChildBO> getSubscribeOrderChild(@Param("orderId") Integer orderId,@Param("orderState") String orderState);
    //根据主订单删除预约中的子订单
    Integer delOrderChild(Integer id);
    //获取预约状态的子订单的联房码
    String getOrderChildAlRoomCode(Integer id);
    //修改子订单 无验证
    Integer updOrderChildNoValidation(OrderChildBO orderChildBO);

    //根据主订单id查询房间信息（客帐管理）
    List<OrderChildBO> getRoomInfoById(Integer orderId);
    //获取在住报表
    List<OrderResult> getCheckInReport(Integer hotelId);
    //获取预离报表
    List<OrderResult> getCheckOutReport(@Param("beforeTime") Date beforeTime,@Param("afterTime") Date afterTime,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize,@Param("hotelId")Integer hotelId);
    //获取预离报表总数
    List<OrderResult> getCheckOutReportCount(@Param("beforeTime") Date beforeTime,@Param("afterTime") Date afterTime,@Param("hotelId")Integer hotelId);
    //把入住未支付超过15分钟的子订单关闭
    Integer closeOrder();
    //获取入住未支付的子订单
    List<OrderChildBO> getCloseOrder();
    //通过房间id查找在住订单信息
    CheckInInfoResult getOrderChildByRoomId(@Param("roomId") Integer roomId,@Param("date") String date);
    //通过房间id查找在住订单信息 未有时间条件
    OrderChildBO getOrderChildByRoomIdNoTime(@Param("roomId") Integer roomId);
    //通过房间id查找预约订单信息
    CheckInInfoResult getReservationInfo(Integer roomId);

    //通过联房码查询联房信息
    List<CheckRoomPersonResult> getOrderRoomByCode(String code);
    //查询所有可用联房
    List<CheckInPersonBO> getAlRoom(@Param("roomId") Integer roomId,@Param("hotelId")Integer hotelId);
    //通过联房码查询子订单
    List<OrderChildBO> getOrderByCode(@Param("code") String code,@Param("main") String main);
    //备份子订单
    Integer addOrderChildBackup(OrderChildBackupParam orderChildBO);
    //查询备份的子订单
    OrderChildBackupParam getOrderChildBackup(Integer id);
    //删除备份的子订单
    Integer delOrderChildBackup(Integer id);
    //获取超时的子订单
    List<OrderChildBO> getTimeOutOrder(String orderState);
    //获取超时的子订单
    List<OrderChildBO> getTimeOutOrder2(String orderState);
    //获取入住支付信息
    List<OrderChildBO> getPayInfo(Integer orderId);


    /**
     * 订单列表
     * @param param
     * @return
     */
    List<OrderListBO> queryOrderList(OrderParam param);

    int queryOrderListCount(OrderParam param);

    BigDecimal queryUnitPrice(Integer id);

    void addOrderRecored(OrderRecoredBO orderRecoredBO);
}

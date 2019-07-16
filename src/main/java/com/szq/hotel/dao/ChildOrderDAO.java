package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.BuyerBuyingBO;
import com.szq.hotel.entity.bo.ChildOrderBO;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.List;

public interface ChildOrderDAO {


    /**
     * 增加子订单现金押金
     */
    void increaseCashCashPledge(@Param("id") Integer childId,@Param("money") BigDecimal money) ;
    /**
     * 减少子订单现金押金
     */
    void reduceCashCashPledge(@Param("id") Integer childId,@Param("money") BigDecimal money) ;
    /**
     * 增加子订单其他押金
     */
    void increaseOtherCashPledge(@Param("id") Integer childId,@Param("money") BigDecimal money) ;
    /**
     * 减少子订单其他押金
     */
    void reduceOtherCashPledge(@Param("id") Integer childId,@Param("money") BigDecimal money) ;

    ChildOrderBO queryOrderChildById(Integer id);

    /**
     * 添加房费
     * @param childId
     * @param money
     */
    void increaseRoomRate(@Param("id") Integer childId,@Param("money") BigDecimal money);

    /**
     * 减少房费
     * @param childId
     * @param money
     */
    void reduceRoomRate(@Param("id") Integer childId,@Param("money") BigDecimal money);
    /**
     * 添加其他消费
     * @param childId
     * @param money
     */
    void increaseOtherRate(@Param("id") Integer childId,@Param("money") BigDecimal money);

    /**
     * 减少其他消费
     * @param childId
     * @param money
     */
    void reduceOtherRate(@Param("id") Integer childId,@Param("money") BigDecimal money);
    /**
     * 查询挂账信息
     * @param roomName
     * @return
     */
    List<BuyerBuyingBO> querySuspend(String roomName);

    /**
     * 免单
     * @param id
     * @param money
     */
    void free(@Param("id") Integer id,@Param("money") BigDecimal money);
    /**
     * 减少免单
     * @param id
     * @param money
     */
    void reducefree(@Param("id") Integer id,@Param("money") BigDecimal money);

    Integer queryOrderChildMain(String code);

    /**
     * 判断是否退房
     * @param list
     * @return
     */
    int ifCheckOut(@Param("list")List<Integer> list);

    void updateOrderUserId(@Param("id") Integer orderChildId,@Param("meId") Integer meId);

    /**
     * 通过id查询订单记录
     */

}

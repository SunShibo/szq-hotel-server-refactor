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
     * 增加子订单其他押金
     */
    void increaseOtherCashPledge(@Param("id") Integer childId,@Param("money") BigDecimal money) ;


    ChildOrderBO queryOrderChildById(Integer id);

    /**
     * 添加房费
     * @param childId
     * @param money
     */
    void increaseRoomRate(@Param("id") Integer childId,@Param("money") BigDecimal money);

    /**
     * 添加其他消费
     * @param childId
     * @param money
     */
    void increaseOtherRate(@Param("id") Integer childId,@Param("money") BigDecimal money);

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

    Integer queryOrderChildMain(String code);
}

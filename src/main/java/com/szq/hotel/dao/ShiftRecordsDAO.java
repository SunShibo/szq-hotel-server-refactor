package com.szq.hotel.dao;



import com.szq.hotel.entity.bo.ShiftRecordsBO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 交班
 */
public interface ShiftRecordsDAO {

    /**
     * 查询是否上班中
     * @param userId
     * @return
     */
    Integer queryIsExist(Integer userId);

    /**
     * 添加记录(上班)
     * @param shiftRecordsBO
     */
    void addShiftRecords(ShiftRecordsBO shiftRecordsBO);

    /**
     * 交班
     */
    void updateShiftRecords(ShiftRecordsBO shiftRecordsBO);

    /**
     * 通过id查询交班记录
     */
    ShiftRecordsBO queryShiftRecordsById(Integer id);


    /**
     *   查询收入
     *   startTime 上班时间
     *   endTime 下班时间
     *   payType 支付方式
     *   userId  用户id
     */
    BigDecimal queryIncome(Map<String,Object> map);
    /**
     *   查询找出
     *   startTime 上班时间
     *   endTime 下班时间
     *   payType 支付方式
     *   userId  用户id
     */
    BigDecimal  queryBack(Map<String,Object> map);
    /**
     * 办卡数量
     *   startTime 上班时间
     *   endTime 下班时间
     *   userId  用户id
     */
    int  queryServizioCount(Map<String,Object> map);

    /**
     * 查询列表页
     *
     */
    List<ShiftRecordsBO> queryShiftRecordList(Map<String,Object> map);
    /**
     * 列表页数量
     */
    int queryShiftRecordCount(Map<String,Object> map);
}

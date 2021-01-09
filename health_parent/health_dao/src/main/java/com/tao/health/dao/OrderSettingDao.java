package com.tao.health.dao;

import com.tao.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/9 19:42
 */
public interface OrderSettingDao {
    /**
     * 通过预约的日期来查询预约设置表
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 调用dao插入数据
     * @param os
     */
    void add(OrderSetting os);

    /**
     *更新最大预约数
     * @param os
     */
    void updateNumber(OrderSetting os);

    /**
     * 查询这个月的预约设置信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    /**
     * 更新已预约人数
     * @param osInDB
     * @return
     */
    int editReservationsByOrderDate(OrderSetting osInDB);
}

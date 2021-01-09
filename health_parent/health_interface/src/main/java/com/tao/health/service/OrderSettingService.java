package com.tao.health.service;

import com.tao.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/9 19:21
 */
public interface OrderSettingService {
    /**
     * 批量导入预约设置
     * @param orderSettingList
     */
    void addBatch(List<OrderSetting> orderSettingList);

    /**
     * 查询这个月的预约设置信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    /**
     * 通过日期设置可预约的最大数
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}

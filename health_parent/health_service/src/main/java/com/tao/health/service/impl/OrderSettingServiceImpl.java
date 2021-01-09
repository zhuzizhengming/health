package com.tao.health.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.tao.health.dao.OrderSettingDao;
import com.tao.health.exception.MyException;
import com.tao.health.pojo.OrderSetting;
import com.tao.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/9 19:21
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入预约设置
     *
     * @param orderSettingList
     */
    @Override
    @Transactional
    public void addBatch(List<OrderSetting> orderSettingList) {
        //判断List<OrderSetting>是否为空
        if (!CollectionUtils.isEmpty(orderSettingList)) {
            //遍历导入的预约设置信息
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM--dd");
            for (OrderSetting os : orderSettingList) {
                //通过预约的日期来查询预约设置表，看这个日期的设置信息有没有
                OrderSetting osInDB = orderSettingDao.findByOrderDate(os.getOrderDate());
                //没有预约设置
                if (osInDB == null) {
                    //调用dao插入数据
                    orderSettingDao.add(os);
                } else {
                    //表中有这个日期的记录。
                    //判断已预约人数是否大于要更新的最大预约数
                    //已预约人数
                    int reservations = osInDB.getReservations();
                    //要更新的最大预约数
                    int number = os.getNumber();
                    if (reservations > number) {
                        //大于则要报错，接口方法，异常声明
                        throw new MyException(sdf.format(os.getOrderDate()) + ":最大预约数不能小于已预约人数");
                    } else {
                        //小于，则可以更新最大预约数
                        orderSettingDao.updateNumber(os);
                    }

                }

            }
        }

    }

    /**
     * 查询这个月的预约设置信息
     *
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        month += month + "%";
        return orderSettingDao.getOrderSettingByMonth(month);
    }

    /**
     * 通过日期设置可预约的最大数
     *
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //- 通过预约的日期来查询预约设置表，看这个日期的设置信息有没有
        OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //- 没有预约设置(表中没有这个日期的记录)
        if (null == osInDB) {
            //  - 调用dao插入数据
            orderSettingDao.add(orderSetting);
        } else {
            //- 有预约设置(表中有这个日期的记录)
            //  - 判断已预约人数是否大于要更新的最大预约数
            // 已预约人数
            int reservations = osInDB.getReservations();
            //要更新的最大预约数
            int number = orderSetting.getNumber();
            if (reservations > number) {
                //  - 大于则要报错，接口方法 异常声明
                throw new MyException(sdf.format(orderSetting.getOrderDate()) + ": 最大预约数不能小于已预约人数");
            } else {
                //  - 小于，则可以更新最大预约数
                orderSettingDao.updateNumber(orderSetting);
            }
        }
    }
}

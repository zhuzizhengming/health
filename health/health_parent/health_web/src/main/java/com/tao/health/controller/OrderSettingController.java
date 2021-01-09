package com.tao.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tao.health.constant.MessageConstant;
import com.tao.health.entity.Result;
import com.tao.health.pojo.OrderSetting;
import com.tao.health.service.OrderSettingService;
import com.tao.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/9 18:04
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量导入预约设置
     *
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        //批量上传：是一个list<string[]>,现在需要将这个转换为javabean
        //解析excel文件：调用POIUtils解析文件，得到List<string[]>,每一个数组就代表一行记录
        try {
            List<String[]> excelData = POIUtils.readExcel(excelFile);
            log.debug("导入预约设置读取到了{}条记录", excelData.size());
            //转成List<Ordersetting>,再调用service方法做导入，返回给页面
            //string[]:中有两个元素：0：日期；1：可预约人数
            final SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> orderSettingList = excelData.stream().map(arr -> {
                OrderSetting os = new OrderSetting();
                try {
                    os.setOrderDate(sdf.parse(arr[0]));
                    os.setNumber(Integer.valueOf(arr[1]));
                } catch (ParseException e) {
                }
                return os;
            }).collect(Collectors.toList());
            //调用服务
            orderSettingService.addBatch(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

        } catch (IOException e) {
            e.printStackTrace();
            log.error("导入预约设置失败",e);
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 查询这个月的预约设置信息
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        List<Map<String,Integer>>data=orderSettingService.getOrderSettingByMonth(month);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,data);
    }

    /**
     * 通过日期设置可预约的最大数
     * @param orderSetting
     * @return
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        //调用服务，进行更新
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}

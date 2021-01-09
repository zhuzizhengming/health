package com.tao.job;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/9 16:11
 * 自定义任务类
 */
public class MyJob {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void abc(){
        System.out.println(sdf.format(new Date()));
    }
}

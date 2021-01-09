package com.tao.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/9 16:32
 */
@Component
public class MyJob_Annotation {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Scheduled 指定触发的时机, 代替jobDetail,trigger
     * 注解所在的对象 targetObject
     * 注解所在的方法 targetMethod
     *
     * cron = "0/2 * * * * ?" 定时
     * 错过了时间点不再执行，会等待下一次执行
     *
     * 每间隔多长时间执行，什么时候启动，间隔时间后就执行
     * cron CronTrigger
     */
//    @Scheduled(cron = "0/2 * * * * ?")

    /**
     * SimpleTrigger
     * initialDelay 启动后延迟执行，单位为ms 3000 3秒
     * fixedDelay: 间隔周期时间 60000 1分钟 6000 6秒
     */
    @Scheduled(initialDelay = 3000,fixedDelay = 10000)
    public void abc() {
        System.out.println("使用普通触发模式:" + sdf.format(new Date()));
    }
}

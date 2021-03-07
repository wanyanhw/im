package com.wanyan.core.service.schedule;

import com.wanyan.util.redis.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class TaskSchedule {
    @Value("${spring.redis.lock}")
    private String lockKey;

    @Autowired
    private RedisOpsUtil redisOpsUtil;

    @Scheduled(cron = "0/10 * * * * ?")
    private void printTimeEvery10Second() {
        Boolean absent = null;
        try {
            long EXPIRE_TIME = 20000;
            absent = redisOpsUtil.setValueIfAbsent(lockKey, lockKey, EXPIRE_TIME);
            if (absent != null && absent) {
                // 获得Redis分布式锁
                System.out.println("-------------------------加锁--------------------");
                System.out.println("-------------------------开始--------------------");
                System.out.println(Thread.currentThread().getName());
                String countKey = "count-key";
                String count = redisOpsUtil.getValue(countKey);
                redisOpsUtil.setValue(countKey, String.valueOf(count == null ? 1 : Integer.parseInt(count) + 1));
                System.out.printf("执行第%s次定时任务\n", redisOpsUtil.getValue(countKey));
                System.out.println("-------------------------结束--------------------");
            }
        } finally {
            if (absent != null && absent) {
                redisOpsUtil.delete(lockKey);
                System.out.println("-------------------------解锁--------------------\n\n");
            }
        }
    }
}

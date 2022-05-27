package com.wanyan.imserver;

import com.wanyan.imserver.server.ImServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

@EnableAsync
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ImServerApplication {

    private static AsyncTaskExecutor asyncTaskExecutor;

    @Resource
    public void setAsyncTaskExecutor(AsyncTaskExecutor executor) {
        asyncTaskExecutor = executor;
    }

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
        asyncTaskExecutor.submit(() -> new ImServer().run("192.168.100.10", 8888));
    }

}

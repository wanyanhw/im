package com.wanyan.imclient;

import com.wanyan.imclient.client.CacheUtil;
import com.wanyan.imclient.client.ImClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

@EnableAsync
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ImClientApplication {

    private static ImClient client;

    @Resource
    public void setClient(ImClient c) {
        client = c;
    }

    public static void main(String[] args) {
        SpringApplication.run(ImClientApplication.class, args);
        String clientName;
        if (args == null || args.length == 0) {
            clientName = "Robot" + (int) (Math.random() * 100);
        } else {
            clientName = args[0];
        }
        CacheUtil.setClientName(clientName);
        client.run("192.168.100.10", 8888);
    }

}

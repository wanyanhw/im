package com.wanyan.imclient;

import com.wanyan.imclient.client.CacheUtil;
import com.wanyan.imclient.client.ImClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ImClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImClientApplication.class, args);
        CacheUtil.setClientName(args[0]);
        new Thread(() -> new ImClient().run("192.168.100.10", 8888)).start();
    }

}

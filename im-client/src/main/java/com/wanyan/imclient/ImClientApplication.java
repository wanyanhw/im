package com.wanyan.imclient;

import com.wanyan.imclient.client.ImClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ImClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImClientApplication.class, args);
        try {
            new ImClient().run("127.0.0.1", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

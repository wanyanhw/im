package com.wanyan.imclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ImClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImClientApplication.class, args);
    }

}

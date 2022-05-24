package com.wanyan.imserver;

import com.wanyan.imserver.server.ImServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ImServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
        try {
            new ImServer().run("192.168.100.10", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

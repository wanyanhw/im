package com.wanyan.imserver;

import com.wanyan.imserver.server.ImServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImServerApplication {

    public static void main(String[] args) {
        try {
            new ImServer().run("127.0.0.1", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

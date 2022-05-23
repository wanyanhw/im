package com.wanyan.imclient;

import com.wanyan.imclient.client.ImClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImClientApplication {

    public static void main(String[] args) {
        try {
            new ImClient().run("127.0.0.1", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

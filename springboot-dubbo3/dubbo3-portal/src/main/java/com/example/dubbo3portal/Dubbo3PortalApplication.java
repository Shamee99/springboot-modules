package com.example.dubbo3portal;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class Dubbo3PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dubbo3PortalApplication.class, args);
    }

}

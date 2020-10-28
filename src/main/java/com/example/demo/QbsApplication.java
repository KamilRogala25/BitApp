package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@SpringBootApplication
public class QbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(QbsApplication.class, args);
    }

}

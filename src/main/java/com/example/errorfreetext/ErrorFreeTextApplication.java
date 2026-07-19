package com.example.errorfreetext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ErrorFreeTextApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErrorFreeTextApplication.class, args);
    }

}


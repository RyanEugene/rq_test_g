package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {

    public static void main(String[] args) {
        System.out.println("RestServiceApplication: main - args = " + args);
        SpringApplication.run(RestServiceApplication.class, args);
    }

}

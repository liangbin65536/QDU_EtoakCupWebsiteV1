package com.qdujava.etoakcup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.qdujava.etoakcup.dao")
public class EtoakcupApplication {
    public static void main(String[] args) {
        SpringApplication.run(EtoakcupApplication.class, args);
    }
}

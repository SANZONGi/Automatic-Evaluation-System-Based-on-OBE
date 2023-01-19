package com.sanzong.obe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author SANZONG
 */
@MapperScan("com.sanzong.obe.mapper")
@SpringBootApplication
public class OBEApplication {
    public static void main(String[] args) {
        SpringApplication.run(OBEApplication.class, args);
    }
}

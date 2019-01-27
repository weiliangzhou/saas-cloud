package com.wegoo.saas.member;

import com.wegoo.currentUser.EnableUserInfoTransmitter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 二师兄
 */
@ComponentScan("com.wegoo")
@SpringBootApplication
@EnableEurekaClient
@ServletComponentScan
@EnableUserInfoTransmitter
@EnableFeignClients
@EnableCaching
public class SaasMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaasMemberApplication.class, args);
    }
}

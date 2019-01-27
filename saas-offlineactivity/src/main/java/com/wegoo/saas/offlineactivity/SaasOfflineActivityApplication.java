package com.wegoo.saas.offlineactivity;

import com.wegoo.currentUser.EnableUserInfoTransmitter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.wegoo")
@SpringBootApplication
@EnableEurekaClient
@ServletComponentScan
@EnableUserInfoTransmitter
@EnableFeignClients
public class SaasOfflineActivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaasOfflineActivityApplication.class, args);
    }
}

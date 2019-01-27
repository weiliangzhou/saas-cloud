package com.wegoo.saaszuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableZuulProxy
@SpringBootApplication
@ComponentScan("com.wegoo")
public class SaasZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaasZuulApplication.class, args);
    }

    /**
     * 加载过滤器
     *
     * @return
     */
    @Bean
    public AccessTokenFilter accessFilter() {
        return new AccessTokenFilter();
    }
}

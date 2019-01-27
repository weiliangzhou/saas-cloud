package com.wegoo.currentUser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 二师兄超级帅 on 2018/10/17.
 */
@Configuration
public class EnableUserInfoTransmitterAutoConfiguration {

    public EnableUserInfoTransmitterAutoConfiguration() {
    }

    @Bean
    public TransmitUserInfoFeighClientIntercepter transmitUserInfo2FeighHttpHeader() {
        return new TransmitUserInfoFeighClientIntercepter();
    }

    @Bean
    public TransmitUserInfoFilter transmitUserInfoFromHttpHeader() {
        return new TransmitUserInfoFilter();
    }
}

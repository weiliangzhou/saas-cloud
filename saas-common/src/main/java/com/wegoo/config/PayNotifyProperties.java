package com.wegoo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 二师兄超级帅
 * @Title: PayNotifyProperties
 * @ProjectName develop_new
 * @Description: TODO
 * @date 2018/8/417:25
 */
@Component
@ConfigurationProperties(prefix = "pay")
@Data
public class PayNotifyProperties {
    private String payNotifyUrl;
    private String qrCodeUrl;
}

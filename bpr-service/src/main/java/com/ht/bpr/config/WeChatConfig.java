package com.ht.bpr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/6 23:41
 * @description
 */
@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WeChatConfig {

    private String appId;
    private String secret;

}
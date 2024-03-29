package com.ht.bpr.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/28 10:19
 * @description
 */
@Configuration
public class TomcatConfig {
    @Value("${http.port}")
    private int httpPort;

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {

            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                if (factory instanceof TomcatServletWebServerFactory) {
                    TomcatServletWebServerFactory webServerFactory = (TomcatServletWebServerFactory)factory;
                    Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
                    // 设置http访问的端口号，不能与https端口重复，否则会报端口被占用的错误
                    connector.setPort(httpPort);
                    webServerFactory.addAdditionalTomcatConnectors(connector);
                }
            }
        };
    }
}

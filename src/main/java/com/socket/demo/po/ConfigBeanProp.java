package com.socket.demo.po;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "demo")
@PropertySource(value = "classpath:config.properties")
public class ConfigBeanProp {
    public String getWife() {
        return wife;
    }

    public void setWife(String wife) {
        this.wife = wife;
    }

    private String wife;
}

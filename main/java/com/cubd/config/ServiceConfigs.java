package com.cubd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("configs.apis")
public class ServiceConfigs {
    private String coinAPI;

    public String getCoinAPI() {
        return coinAPI;
    }

    public void setCoinAPI(String coinAPI) {
        this.coinAPI = coinAPI;
    }
}

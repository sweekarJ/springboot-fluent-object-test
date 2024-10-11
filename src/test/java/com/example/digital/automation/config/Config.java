package com.example.digital.automation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Configuration
@PropertySource("classpath:application-${spring.profiles.active}.yml")
@ConfigurationProperties(prefix = "app")
public class Config {
    HashMap<String, String> portalData;
    public HashMap<String, String> getPortalData() {
        return portalData;
    }

    public void setPortalData(HashMap<String, String> portalData) {
        this.portalData = portalData;
    }

}

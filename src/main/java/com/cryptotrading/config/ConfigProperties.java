package com.cryptotrading.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@Getter
@PropertySource(value = "classpath:application.yml")
public class ConfigProperties {
    @Value("${services.binance.url}")
    private String binanceUrl;
    @Value("${services.huobi.url}")
    private String houbiUrl;
}

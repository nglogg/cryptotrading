package com.cryptotrading.config;

import lombok.Getter;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.SQLException;


@Configuration
@Getter
@PropertySource(value = "classpath:application.yml")
public class ConfigProperties {
    @Value("${services.binance.url}")
    private String binanceUrl;
    @Value("${services.huobi.url}")
    private String houbiUrl;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}

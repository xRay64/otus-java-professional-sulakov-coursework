package ru.sulakov.telegrambot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "exchangeservice")
public class AppConfig {
    private String host;
    private String path;
    private String password;
}

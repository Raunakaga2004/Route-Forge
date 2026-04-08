package me.raunakagarwal.routeforge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public RestTemplate makeRestTemplateBean() {
        return new RestTemplate();
    }
}

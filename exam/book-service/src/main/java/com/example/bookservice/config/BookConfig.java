package com.example.bookservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BookConfig {
    @Bean
    @LoadBalanced
    public RestTemplate RestTemplate() {return new RestTemplate();
    }
}

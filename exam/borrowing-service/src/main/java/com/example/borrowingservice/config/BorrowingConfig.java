package com.example.borrowingservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class BorrowingConfig {
    @Bean
    @LoadBalanced
    public RestTemplate RestTemplate() {return new RestTemplate();
    }
}

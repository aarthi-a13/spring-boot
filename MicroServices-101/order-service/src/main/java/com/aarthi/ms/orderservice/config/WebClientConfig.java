package com.aarthi.ms.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    //http clients - REST, WebClient
    //REST will be in maintenance from spring 5.X and supports only synchronous calls
    //Webclient from spring webflux has a more modern API and supports sync, async, and streaming scenarios.

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}

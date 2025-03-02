package com.kasyus.apigateway.config;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public WebClientConfig(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.lbFunction = lbFunction;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .filter(lbFunction)
                .baseUrl("http://auth-service")
                .build();
    }
}
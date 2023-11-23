package com.kwang23.fountainpen.domain.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class ResilienceConfig {
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Bean
    public CircuitBreaker blogSearchServiceCb() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .minimumNumberOfCalls(3)
                .slidingWindowSize(10)
                .waitDurationInOpenState(Duration.ofSeconds(1))
                .permittedNumberOfCallsInHalfOpenState(3)
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .build();
        return circuitBreakerRegistry.circuitBreaker("blogSearchServiceCb", config);
    }
}

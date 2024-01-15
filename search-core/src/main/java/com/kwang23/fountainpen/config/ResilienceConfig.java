package com.kwang23.fountainpen.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@RequiredArgsConstructor
public class ResilienceConfig {
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Bean
    public CircuitBreaker blogSearchServiceCb() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .slidingWindow(10, 1, CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                .waitDurationInOpenState(Duration.ofSeconds(1))
                .permittedNumberOfCallsInHalfOpenState(3)
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .failureRateThreshold(10) // default 50%
//                .slowCallDurationThreshold()
//                .maxWaitDurationInHalfOpenState()
                .build();
        return circuitBreakerRegistry.circuitBreaker("blogSearchServiceCb", config);
    }
    @Bean
    public Retry keywordRankingRetry() {
        RetryConfig retryConfig = RetryConfig.custom()
                .waitDuration(Duration.of(100, ChronoUnit.MILLIS))
                .failAfterMaxAttempts(true)
//                .retryExceptions()
//                .retryOnException()
//                .retryOnResult()
//                .ignoreExceptions()
                .maxAttempts(3)
                .build();
        RetryRegistry of = RetryRegistry.of(RetryConfig.from(retryConfig).build());
        return of.retry("keywordRankingRetry");
    }
}

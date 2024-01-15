package com.kwang23.fountainpen.searchblog.application.port.in;

import com.kwang23.fountainpen.searchblog.application.port.in.BlogSearchService;
import com.kwang23.fountainpen.searchblog.application.port.in.BlogSearchDto;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State.HALF_OPEN;
import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.config.location=classpath:application.yml,classpath:application-external-fail-test.yml"})
class BlogSearchServiceIntegrationTest {
    @Autowired
    BlogSearchUseCase blogSearchService;
    @Autowired
    CircuitBreakerRegistry circuitBreakerRegistry;

    @Test
    void searchBlog_circuit() {
        CircuitBreaker blogSearchServiceCb = circuitBreakerRegistry.circuitBreaker("blogSearchServiceCb");
        for (int i = 0; i < 10; i++) {
            try {
                if (i == 3) {
                    assertThat(blogSearchServiceCb.getState()).isEqualTo(OPEN);
                    Thread.sleep(1100);
                }
                if (i == 3) {
                    assertThat(blogSearchServiceCb.getState()).isEqualTo(HALF_OPEN);
                }
                if (i >= 6) {
                    assertThat(blogSearchServiceCb.getState()).isEqualTo(OPEN);
                }
                blogSearchService.searchBlog(BlogSearchDto.builder().build());
            } catch (Exception e) {
                e.printStackTrace();
                // ignore
            }
        }
    }
}
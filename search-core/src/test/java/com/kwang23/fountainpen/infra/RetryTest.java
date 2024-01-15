package com.kwang23.fountainpen.infra;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.control.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RetryTest {
    @Autowired
    RetryRegistry retryRegistry;

    @Test
    public void retryOnFailure() {
        Retry retry = retryRegistry.retry("onApplicationEventRetry");
        Runnable runnable = Retry.decorateRunnable(retry, () -> {
            throw new RuntimeException("force exception");
        });

        assertThrows(RuntimeException.class, () -> Try.runRunnable(runnable)
                .onFailure(t -> System.out.println("재시도후 최종 호출 실패 시 : " + t))
                .get());
    }
    @Test
    public void retryOnSuccess() {
        Retry retry = retryRegistry.retry("onApplicationEventRetry");
        Runnable runnable = Retry.decorateRunnable(retry, () -> {
        });
        Try.runRunnable(runnable)
                .onSuccess(t -> System.out.println("성공 시 : " + t))
                .get();
    }
    @Test
    public void retryFailed() {
        Retry retry = retryRegistry.retry("onApplicationEventRetry");
        Runnable runnable = Retry.decorateRunnable(retry, () -> {
            throw new RuntimeException("force exception");
        });
        // 실패 시 예외를 쓰로우 하지 않고 예외 리턴
        Throwable throwable = Try.runRunnable(runnable)
                .failed()
                .get();
        assertThat(throwable).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void retryFailed_noError() {
        Retry retry = retryRegistry.retry("onApplicationEventRetry");
        Runnable runnable = Retry.decorateRunnable(retry, () -> {
            System.out.println("run...");
        });
        // 성공 시 NoSuchElementException throw
        assertThrows(NoSuchElementException.class, () -> Try.runRunnable(runnable)
                .failed()
                .get());

    }
}

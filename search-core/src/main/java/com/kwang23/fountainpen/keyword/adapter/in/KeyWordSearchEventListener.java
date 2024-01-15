package com.kwang23.fountainpen.keyword.adapter.in;

import com.kwang23.fountainpen.keyword.application.port.in.KeyWordCommandService;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;

@RequiredArgsConstructor
@Component
@Slf4j
public class KeyWordSearchEventListener {
    private final KeyWordCommandService keyWordCommandService;
    private final RetryRegistry retryRegistry;

    @Async("keyWordRegistryTaskExecutor")
    @EventListener
    public void onApplicationEvent(KeyWordSearchEvent event) {
        log.info("search word event listen : " + event.getKeyWord());
        Retry retry = retryRegistry.retry("onApplicationEventRetry");
        Runnable runnable = Retry.decorateRunnable(retry, () -> {
            try {
                keyWordCommandService.addKeyWord(event.getKeyWord());
            } catch (NoResultException e) {
                keyWordCommandService.saveKeyWord(event.getKeyWord());
            }
        });
        Try.runRunnable(runnable)
                .onFailure(t -> keyWordCommandService.addKeyWord(event.getKeyWord())).get();
    }
}

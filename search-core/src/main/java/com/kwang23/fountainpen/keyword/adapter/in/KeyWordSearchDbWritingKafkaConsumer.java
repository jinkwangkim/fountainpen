package com.kwang23.fountainpen.keyword.adapter.in;

import com.kwang23.fountainpen.keyword.application.port.in.KeyWordSearchLockService;
import com.kwang23.fountainpen.keyword.application.port.in.KeyWordSearchService;
import com.kwang23.fountainpen.keyword.application.port.out.AddKeyWordPort;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyWordSearchDbWritingKafkaConsumer {
    private final KeyWordSearchService keyWordSearchService;
    private final RetryRegistry retryRegistry;
    private final KeyWordSearchLockService keyWordSearchLockService;

    @KafkaListener(topics = "${kafka.topics.keyWordSearch}", containerFactory = "keyWordSearchEventContainerFactory")
    public void consume(KeyWordSearchEvent event) {
        log.info("search word event listen : " + event.getKeyWord());
        Retry retry = retryRegistry.retry("onApplicationEventRetry");
        Runnable runnable = Retry.decorateRunnable(retry, () -> {
            try {
                keyWordSearchService.addKeyWord(event.getKeyWord());
            } catch (NoResultException e) {
                keyWordSearchService.saveKeyWord(event.getKeyWord());
            }
        });
        Try.runRunnable(runnable)
                .onFailure(t -> keyWordSearchService.addKeyWord(event.getKeyWord())).get();
    }
}

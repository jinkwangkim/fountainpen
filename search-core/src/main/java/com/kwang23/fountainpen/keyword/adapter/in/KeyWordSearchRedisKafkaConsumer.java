package com.kwang23.fountainpen.keyword.adapter.in;

import com.kwang23.fountainpen.keyword.application.port.in.KeyWordSearchLockService;
import com.kwang23.fountainpen.keyword.application.port.out.AddKeyWordPort;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyWordSearchRedisKafkaConsumer {
    private final RetryRegistry retryRegistry;
    @Qualifier("keyWordStatiscsRedisService")
    private final AddKeyWordPort redisService;
    private final KeyWordSearchLockService keyWordSearchLockService;

    @KafkaListener(topics = "${kafka.topics.keyWordSearch}", groupId = "fountainpen-redis",
            containerFactory = "keyWordSearchEventContainerFactory")
    public void consume(KeyWordSearchEvent event) {
        log.info("search word kafka consume : " + event.getKeyWord());
        Retry retry = retryRegistry.retry("onApplicationEventRetry");
        Runnable runnable = Retry.decorateRunnable(retry, () -> {
            redisService.addKeyWord(event.getKeyWord());
        });
        Try.runRunnable(runnable).get();
    }
}

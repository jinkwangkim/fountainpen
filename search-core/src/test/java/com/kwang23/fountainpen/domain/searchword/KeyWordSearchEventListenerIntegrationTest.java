package com.kwang23.fountainpen.domain.searchword;

import com.kwang23.fountainpen.domain.event.KeyWordSearchEvent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.kwang23.fountainpen.domain.searchword.QKeyWordSearch.keyWordSearch;

@SpringBootTest
class KeyWordSearchEventListenerIntegrationTest {
    @Autowired
    KeyWordSearchEventListener listener;
    @Autowired
    JPAQueryFactory queryFactory;
    ThreadPoolTaskExecutor executor;

    @BeforeEach
    public void setup() {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setQueueCapacity(0);
        executor.setMaxPoolSize(100);
        executor.initialize();
    }

    @Test
    void onApplicationEvent() {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                        listener.onApplicationEvent(new KeyWordSearchEvent("abc"));
                        return "S";
                    }
                    , executor));
        }
        futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        Long max = queryFactory.select(keyWordSearch.frequency.max()).from(keyWordSearch).fetchOne();
        Assertions.assertEquals(100L, max);
    }
}
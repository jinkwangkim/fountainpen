package com.kwang23.fountainpen.keyword.adapter.in;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.kwang23.fountainpen.keyword.adapter.out.QKeyWordSearchJpaEntity.keyWordSearchJpaEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class KeyWordSearchDbWritingKafkaConsumerTest {

    @Autowired
    KeyWordSearchDbWritingKafkaConsumer listener;
    @Autowired
    JPAQueryFactory queryFactory;
    ThreadPoolTaskExecutor executor;
    String keyword = "abctest" + System.currentTimeMillis();
    List<String> keyList;
    @BeforeEach
    public void setup() {
        keyList = new ArrayList<>();

        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setQueueCapacity(10000);
        executor.setMaxPoolSize(100);
        executor.initialize();
    }
    @AfterEach
    public void tearDown() {
        queryFactory.delete(keyWordSearchJpaEntity).where(keyWordSearchJpaEntity.keyWord.in(keyList));
    }

    @Test
    void onApplicationEvent() {
        List<CompletableFuture<String>> futures = new ArrayList<>();

        keyList.add(keyword);
        for (int i = 0; i < 100; i++) {
            if(i % 10 == 0) {
                String tmp_keyword =  "abctest" + System.nanoTime();
                keyList.add(tmp_keyword);
            }
            KeyWordSearchEvent keyWordSearchEvent = new KeyWordSearchEvent(keyList.get(keyList.size() - 1));
            futures.add(CompletableFuture.supplyAsync(() -> {
                        listener.consume(keyWordSearchEvent);
                        return "S";
                    }
                    , executor));
        }
        futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        List<Long> fetch = queryFactory.select(keyWordSearchJpaEntity.frequency)
                .from(keyWordSearchJpaEntity)
                .where(keyWordSearchJpaEntity.keyWord.in(keyList))
                .fetch();
        assertThat(fetch.size()).isEqualTo(10L);
        assertThat(fetch).contains(10L);
    }
}
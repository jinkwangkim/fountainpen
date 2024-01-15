package com.kwang23.fountainpen.keyword.adapter.in;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.kwang23.fountainpen.keyword.adapter.out.QKeyWordSearchJpaEntity.keyWordSearchJpaEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
class KeyWordSearchRedisKafkaConsumeIntegrationTest {
    @Autowired
    KeyWordSearchRedisKafkaConsumer consumer;
    @Autowired
    RedisTemplate<String, String> keyWordRedisTemplate;
    ThreadPoolTaskExecutor executor;
    String keyword;
    List<String> keyList;
    String cacheKey;
    @BeforeEach
    public void setup() {
        cacheKey = "keyword:" + LocalDate.now();
        keyList = new ArrayList<>();
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setQueueCapacity(10000);
        executor.setMaxPoolSize(100);
        executor.initialize();
    }

    @AfterEach
    public void tearDown() {
        keyWordRedisTemplate.delete(cacheKey);
    }

    @Test
    void consume() {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        List<String> keyList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if(i % 10 == 0) {
                String tmpKeyword =  "abctest" + System.nanoTime();
                keyList.add(tmpKeyword);
            }
            KeyWordSearchEvent keyWordSearchEvent = new KeyWordSearchEvent(keyList.get(keyList.size() - 1));
            futures.add(CompletableFuture.supplyAsync(() -> {
                        consumer.consume(keyWordSearchEvent);
                        return "S";
                    } , executor));
        }
        futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        ZSetOperations<String, String> ops = keyWordRedisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ops.rangeWithScores(cacheKey, 0, 100);

        assertThat(typedTuples).extracting("value")
                .contains(keyList.toArray());
        assertThat(typedTuples).extracting("score").containsOnly(10.0D);
    }
}
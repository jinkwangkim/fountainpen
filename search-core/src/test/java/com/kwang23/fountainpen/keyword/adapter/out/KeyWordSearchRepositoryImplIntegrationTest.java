package com.kwang23.fountainpen.keyword.adapter.out;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchDto;
import com.kwang23.fountainpen.keyword.adapter.out.KeyWordSearchJpaEntity;
import com.kwang23.fountainpen.keyword.adapter.out.KeyWordSearchRepository;
import com.kwang23.fountainpen.config.JpaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(JpaConfig.class)
@Transactional
class KeyWordSearchRepositoryImplIntegrationTest {
    @Autowired
    KeyWordSearchRepository keyWordSearchRepository;
    @PersistenceContext
    EntityManager em;
    ThreadPoolTaskExecutor executor;

    @BeforeEach
    public void setup() {
        KeyWordSearchJpaEntity word1 = new KeyWordSearchJpaEntity("abc", Long.MAX_VALUE - 2, LocalDate.now());
        KeyWordSearchJpaEntity word2 = new KeyWordSearchJpaEntity("abd", Long.MAX_VALUE - 1, LocalDate.now());
        KeyWordSearchJpaEntity word3 = new KeyWordSearchJpaEntity("abe", Long.MAX_VALUE, LocalDate.now());
        em.persist(word1);
        em.persist(word2);
        em.persist(word3);

        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setQueueCapacity(0);
        executor.setMaxPoolSize(20);
        executor.initialize();
    }

    @Test
    void findKeyWordList() {
        List<KeyWordSearchDto> ab = keyWordSearchRepository.findKeyWordList(3);
        assertThat(ab.size()).isEqualTo(3);
        assertThat(ab).extracting(KeyWordSearchDto::getKeyWord).contains("abc", "abd", "abe");
        assertThat(ab).extracting(KeyWordSearchDto::getFrequency).contains(Long.MAX_VALUE, Long.MAX_VALUE - 1, Long.MAX_VALUE -2);
    }

    @Test
    void findKeyWordList_size() {
        List<KeyWordSearchDto> ab = keyWordSearchRepository.findKeyWordList(2);
        assertThat(ab.size()).isEqualTo(2);
        assertThat(ab).extracting(KeyWordSearchDto::getKeyWord).contains("abe", "abd");
        assertThat(ab).extracting(KeyWordSearchDto::getFrequency).contains(Long.MAX_VALUE, Long.MAX_VALUE - 1);
    }
    @Test
    void findByKeyWordAndTargetDateForUpdate() {
        KeyWordSearchJpaEntity k = keyWordSearchRepository.findByKeyWordAndTargetDateForUpdate("abe", LocalDate.now());
        k.addFrequency();
    }
}
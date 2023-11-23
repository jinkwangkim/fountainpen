package com.kwang23.fountainpen.domain.searchword;

import com.kwang23.fountainpen.domain.config.JpaConfig;
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
        KeyWordSearch word1 = new KeyWordSearch("abc", 2, LocalDate.now());
        KeyWordSearch word2 = new KeyWordSearch("abd", 3, LocalDate.now());
        KeyWordSearch word3 = new KeyWordSearch("abe", 4, LocalDate.now());
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
        assertThat(ab).extracting(KeyWordSearchDto::getFrequency).contains(4L, 3L, 2L);
    }

    @Test
    void findKeyWordList_size() {
        List<KeyWordSearchDto> ab = keyWordSearchRepository.findKeyWordList(2);
        assertThat(ab.size()).isEqualTo(2);
        assertThat(ab).extracting(KeyWordSearchDto::getKeyWord).contains("abe", "abd");
        assertThat(ab).extracting(KeyWordSearchDto::getFrequency).contains(4L, 3L);
    }
}
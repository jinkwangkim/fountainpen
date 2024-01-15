package com.kwang23.fountainpen.keyword.application.port.in;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchDto;
import com.kwang23.fountainpen.keyword.adapter.out.KeyWordSearchJpaEntity;
import com.kwang23.fountainpen.keyword.adapter.out.KeyWordSearchRepository;
import com.kwang23.fountainpen.keyword.application.port.out.AddKeyWordPort;
import com.kwang23.fountainpen.keyword.application.port.out.KeyWordSearchPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeyWordCommandService implements AddKeyWordPort {
    private final KeyWordSearchRepository keyWordSearchRepository;
    private final KeyWordSearchPort keyWordSearchPort;

    @Transactional(readOnly = true)
    public List<KeyWordSearchDto> getSearchWordList(int size) {
        return keyWordSearchRepository.findKeyWordList(size);
    }

    @Transactional
    public void addKeyWord(String keyWord) {
        KeyWordSearchJpaEntity keyWordSearch = keyWordSearchPort.searchKeyWordWithLock(keyWord, LocalDate.now());
        if (Objects.isNull(keyWordSearch)) {
            throw new NoResultException();
        }
        keyWordSearch.addFrequency();
        log.info("update : " + keyWordSearch);

    }

    @Transactional
    public void addKeyWordNoDbLock(String keyWord) {
        KeyWordSearchJpaEntity keyWordSearch = keyWordSearchRepository.findByKeyWordAndTargetDate(keyWord, LocalDate.now());
        if (Objects.isNull(keyWordSearch)) {
            keyWordSearch = new KeyWordSearchJpaEntity(keyWord, 1, LocalDate.now());
            keyWordSearch = keyWordSearchRepository.save(keyWordSearch);
            return;
        }
        keyWordSearch.addFrequency();
        log.info("update : " + keyWordSearch);
    }

    @Transactional
    public KeyWordSearchJpaEntity saveKeyWord(String keyWord) {
        KeyWordSearchJpaEntity keyWordSearch = keyWordSearchRepository.findByKeyWordAndTargetDate(keyWord, LocalDate.now());
        if (Objects.isNull(keyWordSearch)) {
            keyWordSearch = new KeyWordSearchJpaEntity(keyWord, 1, LocalDate.now());
            return keyWordSearchRepository.save(keyWordSearch);
        } else {
            throw new IllegalStateException();
        }
    }
}

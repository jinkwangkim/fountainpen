package com.kwang23.fountainpen.domain.searchword;

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
public class KeyWordSearchService {
    private final KeyWordSearchRepository keyWordSearchRepository;

    @Transactional(readOnly = true)
    public List<KeyWordSearchDto> getSearchWordList(int size) {
        return keyWordSearchRepository.findKeyWordList(size);
    }

    @Transactional
    public void addKeyWord(String keyWord) {
        KeyWordSearch keyWordSearch = keyWordSearchRepository.findByKeyWordAndTargetDateForUpdate(keyWord, LocalDate.now());
        if (Objects.isNull(keyWordSearch)) {
            throw new NoResultException();
        }
        keyWordSearch.addFrequency();
        log.info("update : " + keyWordSearch);
    }

    @Transactional
    public void saveKeyWord(String keyWord) {
        KeyWordSearch keyWordSearch = keyWordSearchRepository.findByKeyWordAndTargetDate(keyWord, LocalDate.now());
        if (Objects.isNull(keyWordSearch)) {
            keyWordSearch = new KeyWordSearch(keyWord, 1, LocalDate.now());
            keyWordSearchRepository.save(keyWordSearch);
        } else {
            throw new IllegalStateException();
        }
    }
}

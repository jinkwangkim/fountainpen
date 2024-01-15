package com.kwang23.fountainpen.keyword.adapter.out;

import com.kwang23.fountainpen.keyword.application.port.out.KeyWordSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class KeyWordSearchJpaAdapter implements KeyWordSearchPort {
    private final KeyWordSearchRepository keyWordSearchRepository;
    @Override
    public KeyWordSearchJpaEntity searchKeyWordWithLock(String keyWord, LocalDate targetDate) {
        return keyWordSearchRepository.findByKeyWordAndTargetDateForUpdate(keyWord, targetDate);
    }
}

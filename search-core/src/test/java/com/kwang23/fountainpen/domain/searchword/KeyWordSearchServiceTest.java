package com.kwang23.fountainpen.domain.searchword;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KeyWordSearchServiceTest {
    @InjectMocks
    KeyWordSearchService keyWordSearchService;
    @Mock
    KeyWordSearchRepository keyWordSearchRepository;

    @Test
    void getSearchWordList() {
        KeyWordSearchDto keyWordSearchDto = new KeyWordSearchDto("keyWord", 100, LocalDate.now());
        when(keyWordSearchRepository.findKeyWordList(anyInt())).thenReturn(List.of(keyWordSearchDto));

        List<KeyWordSearchDto> searchWordList = keyWordSearchService.getSearchWordList(10);

        assertThat(searchWordList.size()).isEqualTo(1);
        KeyWordSearchDto keyWordSearchDtoResult = searchWordList.get(0);
        assertThat(keyWordSearchDtoResult.getKeyWord()).isEqualTo(keyWordSearchDto.getKeyWord());
        assertThat(keyWordSearchDtoResult.getFrequency()).isEqualTo(keyWordSearchDto.getFrequency());
    }

    @Test
    void addKeyWord() {
        String keyWord = "keyWord";
        KeyWordSearch keyWordSearch = new KeyWordSearch(keyWord, 100, LocalDate.now());
        when(keyWordSearchRepository.findByKeyWordAndTargetDateForUpdate(anyString(), any())).thenReturn(keyWordSearch);
        TransactionSynchronizationManager.initSynchronization();

        keyWordSearchService.addKeyWord(keyWord);
        TransactionSynchronizationManager.getSynchronizations().forEach(TransactionSynchronization::afterCommit);

        assertThat(keyWordSearch.getFrequency()).isEqualTo(101);

    }
}
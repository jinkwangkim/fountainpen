package com.kwang23.fountainpen.domain.searchword;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class KeyWordSearchDto {
    private final String keyWord;
    private final long frequency;
    private final LocalDate targetDate;
}

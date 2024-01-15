package com.kwang23.fountainpen.keyword.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@ToString
public class KeyWordSearchDto {
    private final String keyWord;
    private final long frequency;
    private final LocalDate targetDate;
}

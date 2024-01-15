package com.kwang23.fountainpen.keyword.application.port.out;

import com.kwang23.fountainpen.keyword.adapter.out.KeyWordSearchJpaEntity;

import java.time.LocalDate;

public interface KeyWordSearchPort {
    KeyWordSearchJpaEntity searchKeyWordWithLock(String keyWord, LocalDate targetDate);
}

package com.kwang23.fountainpen.keyword.adapter.out;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchDto;

import java.util.List;

public interface KeyWordSearchRepositoryCustom {
    List<KeyWordSearchDto> findKeyWordList(int size);
}

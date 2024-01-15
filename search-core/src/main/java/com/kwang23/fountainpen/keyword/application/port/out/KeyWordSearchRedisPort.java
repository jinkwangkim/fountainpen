package com.kwang23.fountainpen.keyword.application.port.out;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchDto;

import java.util.List;

public interface KeyWordSearchRedisPort {
    List<KeyWordSearchDto> getTopTenKeyWord(int size);
}

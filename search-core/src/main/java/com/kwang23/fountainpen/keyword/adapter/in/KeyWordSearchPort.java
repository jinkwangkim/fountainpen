package com.kwang23.fountainpen.keyword.adapter.in;

import java.util.List;

public interface KeyWordSearchPort {
    List<KeyWordSearchDto> getTopTenKeyWord(int size);
}

package com.kwang23.fountainpen.domain.searchword;

import java.util.List;

public interface KeyWordSearchRepositoryCustom {
    List<KeyWordSearchDto> findKeyWordList(int size);
}

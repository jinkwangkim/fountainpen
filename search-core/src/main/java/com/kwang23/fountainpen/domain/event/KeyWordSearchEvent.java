package com.kwang23.fountainpen.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class KeyWordSearchEvent {
    private final String keyWord;

}

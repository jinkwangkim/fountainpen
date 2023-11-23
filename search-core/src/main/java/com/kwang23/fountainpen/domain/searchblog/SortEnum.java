package com.kwang23.fountainpen.domain.searchblog;

import com.querydsl.core.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum SortEnum {
    ACCURACY("accuracy", "sim"), RECENCY("recency", "date");
    private final String kakao;
    private final String naver;

    SortEnum(String kakao, String naver) {
        this.kakao = kakao;
        this.naver = naver;
    }

    public String getValue() {
        return this.kakao;
    }

    public String failover() {
        return this.naver;
    }

    public static Optional<SortEnum> getEnum(String value) {
        if (StringUtils.isNullOrEmpty(value)) return Optional.empty();
        return Arrays.stream(SortEnum.values())
                .filter(e -> e.getValue().equals(value) || e.failover().equals(value))
                .findFirst();
    }
}

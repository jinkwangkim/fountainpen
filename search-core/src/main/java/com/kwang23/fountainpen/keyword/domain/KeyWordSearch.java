package com.kwang23.fountainpen.keyword.domain;

import com.kwang23.fountainpen.infra.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@ToString
public class KeyWordSearch {
    private Long id;
    private String keyWord;
    private long frequency;
    private LocalDate targetDate;

    public KeyWordSearch(String keyWord, long frequency, LocalDate targetDate) {
        this.keyWord = keyWord;
        this.frequency = frequency;
        this.targetDate = targetDate;
    }

    public long addFrequency() {
        this.frequency += 1;
        return this.frequency;
    }
}

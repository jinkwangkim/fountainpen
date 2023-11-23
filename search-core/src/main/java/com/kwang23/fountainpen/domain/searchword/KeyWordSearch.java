package com.kwang23.fountainpen.domain.searchword;

import com.kwang23.fountainpen.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "key_word_search", indexes = {
        @Index(name = "key_word_search_unique_idx01", columnList = "keyWord,targetDate", unique = true),
        @Index(name = "key_word_search_idx02", columnList = "keyWord,targetDate,frequency DESC"),
        @Index(name = "key_word_search_idx03", columnList = "createdAt"),
        @Index(name = "key_word_search_idx04", columnList = "updatedAt")
})
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyWordSearch extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

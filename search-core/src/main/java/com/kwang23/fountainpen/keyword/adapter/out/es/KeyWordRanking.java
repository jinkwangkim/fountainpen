package com.kwang23.fountainpen.keyword.adapter.out.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(indexName = "keyword_ranking")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyWordRanking {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String keyWord;
    @Field(type = FieldType.Long)
    private long frequency;
    @Field(type = FieldType.Date, index = false, store = true, format = DateFormat.custom, pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;

    public KeyWordRanking(String keyWord, LocalDate targetDate) {
        this.keyWord = keyWord;
        this.frequency = 1L;
        this.targetDate = targetDate;
        this.updatedAt = LocalDateTime.now();
    }

}

package com.kwang23.fountainpen.external.api.blog.naver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kwang23.fountainpen.external.api.blog.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverDocument {
    private String bloggername;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate postdate;
    private String title;
    private String link;

    public Document toDocument() {
        return Document.builder()
                .url(link)
                .blogname(bloggername)
                .contents(description)
                .datetime(postdate.atStartOfDay())
                .title(title)
                .build();
    }
}

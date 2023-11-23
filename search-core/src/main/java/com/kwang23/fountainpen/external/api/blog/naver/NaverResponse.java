package com.kwang23.fountainpen.external.api.blog.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kwang23.fountainpen.external.api.blog.Document;
import com.kwang23.fountainpen.external.api.blog.ExternalSearchResult;
import com.kwang23.fountainpen.external.api.blog.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"items"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverResponse {
    private List<NaverDocument> items;
    private int display;
    private int start;
    private int total;

    public ExternalSearchResult toExternalSearchResult() {
        List<Document> documents = new ArrayList<>();
        if (items != null) {
            documents = items.stream()
                    .map(NaverDocument::toDocument)
                    .collect(Collectors.toList());
        }
        int mod = total % display;
        int lastPage = total / display + (mod > 0 ? 1 : 0);
        boolean isEnd = start == lastPage;

        return new ExternalSearchResult(documents, new Meta(isEnd, total, total));
    }
}

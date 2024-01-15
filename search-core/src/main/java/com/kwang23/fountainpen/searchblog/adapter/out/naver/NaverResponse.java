package com.kwang23.fountainpen.searchblog.adapter.out.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kwang23.fountainpen.searchblog.adapter.out.Document;
import com.kwang23.fountainpen.searchblog.adapter.out.ExternalSearchResult;
import com.kwang23.fountainpen.searchblog.adapter.out.Meta;
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

package com.kwang23.fountainpen.searchblog.adapter.out;

import com.kwang23.fountainpen.searchblog.application.port.in.SortEnum;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlogSearchParameters {
    private String targetBlogUrl;
    private String query;
    private SortEnum sort;
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;

    public String queryParameters() {
        String queryParameters = "query=" + targetBlogUrl + " " + query + "&page=" + page + "&size=" + size;
        if (sort != null) {
            queryParameters += "&sort=" + sort.getValue();
        }
        return queryParameters;
    }

    public String queryParametersForNaver() {
        String queryParameters = "query=" + query + "&start=" + page + "&display=" + size;
        if (sort != null) {
            queryParameters += "&sort=" + sort.failover();
        }
        return queryParameters;
    }
}

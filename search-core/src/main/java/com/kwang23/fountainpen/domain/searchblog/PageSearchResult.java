package com.kwang23.fountainpen.domain.searchblog;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageSearchResult {
    private final List<BlogInfo> blogInfoList;
    private final int totalCount;
    private final int currentPage;
    private final int size;

}

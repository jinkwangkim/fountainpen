package com.kwang23.fountainpen.searchblog.application.port.in;

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

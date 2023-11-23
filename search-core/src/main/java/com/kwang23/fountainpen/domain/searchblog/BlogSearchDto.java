package com.kwang23.fountainpen.domain.searchblog;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlogSearchDto {
    @Builder.Default
    private String targetBlog = "";
    private String keyWord;
    private SortEnum sort;
    private int page;
    private int size;
}

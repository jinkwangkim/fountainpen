package com.kwang23.fountainpen.domain.searchblog;

import com.kwang23.fountainpen.external.api.blog.Document;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class BlogInfo {
    private final String blogname;
    private final String contents;
    private final LocalDateTime datetime;
    private final String thumbnail;
    private final String title;
    private final String url;

    public static BlogInfo of(Document document) {
        return new BlogInfo(document.getBlogname(), document.getContents(), document.getDatetime(), document.getThumbnail(), document.getTitle(), document.getUrl());
    }
}

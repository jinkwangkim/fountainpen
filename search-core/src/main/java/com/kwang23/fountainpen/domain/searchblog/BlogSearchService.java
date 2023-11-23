package com.kwang23.fountainpen.domain.searchblog;

import com.kwang23.fountainpen.domain.event.KeyWordSearchEvent;
import com.kwang23.fountainpen.external.api.blog.BlogSearchExternalApiAdapter;
import com.kwang23.fountainpen.external.api.blog.BlogSearchParameters;
import com.kwang23.fountainpen.external.api.blog.ExternalSearchResult;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogSearchService {
    @Qualifier("blogSearchApiAdapterWithFallback")
    private final BlogSearchExternalApiAdapter blogSearchExternalApiAdapter;
    private final ApplicationEventPublisher publisher;

    @CircuitBreaker(name = "blogSearchServiceCb", fallbackMethod = "searchBlogFallback")
    public PageSearchResult searchBlog(BlogSearchDto blogSearch) {
        ExternalSearchResult result = blogSearchExternalApiAdapter.search(toBlogSearchParameters(blogSearch));
        publisher.publishEvent(new KeyWordSearchEvent(blogSearch.getKeyWord()));
        return PageSearchResult.builder()
                .blogInfoList(result.getDocuments().stream()
                        .map(BlogInfo::of)
                        .collect(Collectors.toList()))
                .totalCount(result.getMeta().getPageableCount())
                .currentPage(blogSearch.getPage())
                .size(blogSearch.getSize())
                .build();
    }

    private PageSearchResult searchBlogFallback(BlogSearchDto blogSearch, Throwable t) {
        log.error("Called searchBlogFallback", t);
        return PageSearchResult.builder()
                .blogInfoList(Collections.EMPTY_LIST)
                .currentPage(blogSearch.getPage())
                .totalCount(0)
                .size(blogSearch.getSize())
                .build();
    }

    private BlogSearchParameters toBlogSearchParameters(BlogSearchDto blogSearch) {
        String blogUrl = StringUtils.hasText(blogSearch.getTargetBlog()) ? blogSearch.getTargetBlog() : "";
        return new BlogSearchParameters(blogUrl, blogSearch.getKeyWord(), blogSearch.getSort(), blogSearch.getPage(), blogSearch.getSize());
    }
}

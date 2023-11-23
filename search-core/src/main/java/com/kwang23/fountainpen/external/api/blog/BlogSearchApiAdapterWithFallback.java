package com.kwang23.fountainpen.external.api.blog;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BlogSearchApiAdapterWithFallback implements BlogSearchExternalApiAdapter {
    private final BlogSearchExternalApiAdapter kakaoBlogSearchApiAdapter;
    @Qualifier("naverBlogSearchApiAdapter")
    private final BlogSearchExternalApiAdapter fallback;

    @Override
    @Retry(name = "blogSearchApiAdapterWithFallbackRetry", fallbackMethod = "fallback")
    public ExternalSearchResult search(BlogSearchParameters searchBlog) {
        return kakaoBlogSearchApiAdapter.search(searchBlog);
    }

    private ExternalSearchResult fallback(BlogSearchParameters searchBlog, Throwable t) {
        log.error("Called fallback. parameters : " + searchBlog, t);

        return fallback.search(searchBlog);
    }
}

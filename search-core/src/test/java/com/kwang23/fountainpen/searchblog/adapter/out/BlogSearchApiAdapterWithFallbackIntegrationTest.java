package com.kwang23.fountainpen.searchblog.adapter.out;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BlogSearchApiAdapterWithFallbackIntegrationTest {
    @Autowired
    BlogSearchApiAdapterWithFallback blogSearchApiAdapterWithFallback;

    @Test
    void search_retry_fallback() {
        BlogSearchParameters searchBlog = BlogSearchParameters.builder().query("test").page(2).size(3).build();
        ExternalSearchResult search = blogSearchApiAdapterWithFallback.search(searchBlog);

        assertThat(search).isNotNull();
        assertThat(search.getMeta()).isNotNull();
        assertThat(search.getDocuments()).isNotNull();
    }
}
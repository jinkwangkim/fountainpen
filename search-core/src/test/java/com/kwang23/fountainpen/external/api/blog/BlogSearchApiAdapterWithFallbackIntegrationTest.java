package com.kwang23.fountainpen.external.api.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.config.location=classpath:application-external-test.yml"})
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
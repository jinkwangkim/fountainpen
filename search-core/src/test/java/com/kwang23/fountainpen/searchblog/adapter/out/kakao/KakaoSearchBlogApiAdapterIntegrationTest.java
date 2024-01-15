package com.kwang23.fountainpen.searchblog.adapter.out.kakao;

import com.kwang23.fountainpen.searchblog.adapter.out.BlogSearchParameters;
import com.kwang23.fountainpen.searchblog.adapter.out.ExternalSearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KakaoSearchBlogApiAdapterIntegrationTest {
    @Autowired
    KakaoBlogSearchApiAdapter adapter;

    @Test
    void testSearch() {
        BlogSearchParameters searchBlog = BlogSearchParameters.builder().query("test").build();
        ExternalSearchResult search = adapter.search(searchBlog);
        assertThat(search).isNotNull();
        assertThat(search.getMeta()).isNotNull();
        assertThat(search.getDocuments()).isNotNull();
    }
}
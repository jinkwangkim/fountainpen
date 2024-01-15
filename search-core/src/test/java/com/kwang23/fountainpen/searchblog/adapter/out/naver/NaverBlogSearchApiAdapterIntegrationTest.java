package com.kwang23.fountainpen.searchblog.adapter.out.naver;

import com.kwang23.fountainpen.searchblog.adapter.out.naver.NaverBlogSearchApiAdapter;
import com.kwang23.fountainpen.searchblog.adapter.out.BlogSearchParameters;
import com.kwang23.fountainpen.searchblog.adapter.out.ExternalSearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NaverBlogSearchApiAdapterIntegrationTest {
    @Autowired
    NaverBlogSearchApiAdapter adapter;

    @Test
    void search() {
        BlogSearchParameters searchBlog = BlogSearchParameters.builder().query("test").build();
        ExternalSearchResult search = adapter.search(searchBlog);
        assertThat(search).isNotNull();
        assertThat(search.getMeta()).isNotNull();
        assertThat(search.getDocuments()).isNotNull();
    }
}
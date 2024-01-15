package com.kwang23.fountainpen.searchblog.adapter.out.kakao;

import com.kwang23.fountainpen.searchblog.adapter.out.BlogSearchApiRestTemplate;
import com.kwang23.fountainpen.searchblog.adapter.out.BlogSearchExternalApiAdapter;
import com.kwang23.fountainpen.searchblog.adapter.out.BlogSearchParameters;
import com.kwang23.fountainpen.searchblog.adapter.out.ExternalSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KakaoBlogSearchApiAdapter implements BlogSearchExternalApiAdapter {
    @Value("${external.api.kakao.url}")
    private String url;
    @Value("${external.api.kakao.restKey}")
    private String restKey;

    @Override
    public ExternalSearchResult search(BlogSearchParameters searchBlog) {
        BlogSearchApiRestTemplate<ExternalSearchResult> restTemplate = new BlogSearchApiRestTemplate<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + restKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = this.url + searchBlog.queryParameters();
        return restTemplate.search(headers, url, ExternalSearchResult.class);
    }
}

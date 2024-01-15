package com.kwang23.fountainpen.searchblog.adapter.out.naver;

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
public class NaverBlogSearchApiAdapter implements BlogSearchExternalApiAdapter {
    public static final String HEADER_X_NAVER_CLIENT_ID = "X-Naver-Client-Id";
    public static final String HEADER_X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";
    @Value("${external.api.naver.url}")
    private String url;
    @Value("${external.api.naver.clientId}")
    private String clientId;
    @Value("${external.api.naver.clientSecret}")
    private String clientSecret;

    @Override
    public ExternalSearchResult search(BlogSearchParameters searchBlog) {
        BlogSearchApiRestTemplate<NaverResponse> restTemplate = new BlogSearchApiRestTemplate<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_X_NAVER_CLIENT_ID, clientId);
        headers.set(HEADER_X_NAVER_CLIENT_SECRET, clientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = this.url + searchBlog.queryParametersForNaver();
        NaverResponse response = restTemplate.search(headers, url, NaverResponse.class);
        return response.toExternalSearchResult();
    }
}

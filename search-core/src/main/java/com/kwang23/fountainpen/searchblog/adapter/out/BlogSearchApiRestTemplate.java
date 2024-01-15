package com.kwang23.fountainpen.searchblog.adapter.out;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
public class BlogSearchApiRestTemplate<T> {

    public T search(HttpHeaders headers, String url, Class<T> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .build()
                .toUri();

        ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, entity, responseType);
        HttpStatus statusCode = response.getStatusCode();
        if (log.isDebugEnabled()) {
            log.debug("status code : " + statusCode);
            log.debug("body : " + response.getBody());
        }
        return response.getBody();
    }
}

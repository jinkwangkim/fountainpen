package com.kwang23.fountainpen.searchblog.application.port.in;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchEvent;
import com.kwang23.fountainpen.infra.utils.TransactionSynchronizationFunc;
import com.kwang23.fountainpen.searchblog.adapter.out.BlogSearchExternalApiAdapter;
import com.kwang23.fountainpen.searchblog.adapter.out.BlogSearchParameters;
import com.kwang23.fountainpen.searchblog.adapter.out.ExternalSearchResult;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogSearchService implements BlogSearchUseCase {
    @Qualifier("blogSearchApiAdapterWithFallback")
    private final BlogSearchExternalApiAdapter blogSearchExternalApiAdapter;
    private final KafkaTemplate<String, KeyWordSearchEvent> keyWordSearchEventKafkaTemplate;

    @CircuitBreaker(name = "blogSearchServiceCb", fallbackMethod = "searchBlogFallback")
    @Transactional(readOnly = true)
    public PageSearchResult searchBlog(BlogSearchDto blogSearch) {
        ExternalSearchResult result = blogSearchExternalApiAdapter.search(toBlogSearchParameters(blogSearch));

        TransactionSynchronizationFunc.builer()
                .afterCommit(() -> {
                    ProducerRecord<String, KeyWordSearchEvent> record = new ProducerRecord(
                            "keyword-search-event", blogSearch.getKeyWord(), new KeyWordSearchEvent(blogSearch.getKeyWord()));
                    keyWordSearchEventKafkaTemplate.send(record);
                }).regist();

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

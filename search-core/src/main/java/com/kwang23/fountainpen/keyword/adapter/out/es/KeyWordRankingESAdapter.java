package com.kwang23.fountainpen.keyword.adapter.out.es;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchDto;
import com.kwang23.fountainpen.keyword.application.port.out.AddKeyWordPort;
import com.kwang23.fountainpen.keyword.application.port.out.KeyWordListSearchPort;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KeyWordRankingESAdapter implements AddKeyWordPort, KeyWordListSearchPort {
    private final KeyWordRankingRepository keyWordRankingRepository;
    private final RestHighLevelClient client;
    @Override
    public void addKeyWord(String keyWord) {
        KeyWordRanking keyWordRanking = new KeyWordRanking(keyWord, LocalDate.now());
        keyWordRankingRepository.save(keyWordRanking);
    }

    @Override
    public List<KeyWordSearchDto> aggregateKeyWordRanking(String indexName) {
        TermsAggregationBuilder aggregation = AggregationBuilders
                .terms("top_keywords")
                .field("keyWord")
                .size(10)
                .order(BucketOrder.aggregation("frequency", false))
                .subAggregation(AggregationBuilders.sum("frequency").field("frequency"));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggregation);
        searchSourceBuilder.size(0);
        LocalDate today = LocalDate.now();
        searchSourceBuilder.query(QueryBuilders.matchQuery("targetDate", today.format(DateTimeFormatter.ISO_DATE)));

        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ParsedStringTerms topKeywords = searchResponse.getAggregations().get("top_keywords");

        List<KeyWordSearchDto> topKeywordsList = new ArrayList<>();
        for (Terms.Bucket bucket : topKeywords.getBuckets()) {
            String keyword = bucket.getKeyAsString();
            long frequency = bucket.getDocCount();
            topKeywordsList.add(new KeyWordSearchDto(keyword, frequency, today));
        }
        return topKeywordsList;
    }
}

package com.kwang23.fountainpen.keyword.application.port.in;

import com.kwang23.fountainpen.keyword.adapter.out.es.KeyWordRankingRepository;
import com.kwang23.fountainpen.keyword.adapter.out.es.KeyWordRanking;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class KeyWordRankingService {
    @Qualifier("keyWordCommandService")
    private final AddKeyWordPort addKeyWordPort;
    private final KeyWordListSearchPort keyWordListSearchPort;

    public void addKeyword(String keyWord) {
        addKeyWordPort.addKeyWord(keyWord);
    }

    public List<KeyWordSearchDto> aggregateKeyWordRanking(String indexName) throws IOException {
        return keyWordListSearchPort.aggregateKeyWordRanking(indexName);
    }
}

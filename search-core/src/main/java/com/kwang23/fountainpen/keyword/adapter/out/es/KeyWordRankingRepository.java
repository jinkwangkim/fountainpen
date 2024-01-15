package com.kwang23.fountainpen.keyword.adapter.out.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface KeyWordRankingRepository extends ElasticsearchRepository<KeyWordRanking, Long>, CrudRepository<KeyWordRanking, Long> {
}

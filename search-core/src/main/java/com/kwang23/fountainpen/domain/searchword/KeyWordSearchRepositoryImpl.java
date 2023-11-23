package com.kwang23.fountainpen.domain.searchword;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.kwang23.fountainpen.domain.searchword.QKeyWordSearch.keyWordSearch;


@RequiredArgsConstructor
public class KeyWordSearchRepositoryImpl implements KeyWordSearchRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<KeyWordSearchDto> findKeyWordList(int size) {
        return queryFactory.select(Projections.constructor(KeyWordSearchDto.class, keyWordSearch.keyWord, keyWordSearch.frequency, keyWordSearch.targetDate))
                .from(keyWordSearch)
                .where(keyWordSearch.frequency.gt(0)
                        .and(keyWordSearch.targetDate.eq(LocalDate.now())))
                .orderBy(keyWordSearch.frequency.desc())
                .limit(size)
                .fetch();
    }
}

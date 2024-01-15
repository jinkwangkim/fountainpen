package com.kwang23.fountainpen.keyword.adapter.out;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.kwang23.fountainpen.keyword.adapter.out.QKeyWordSearchJpaEntity.*;

@RequiredArgsConstructor
public class KeyWordSearchRepositoryImpl implements KeyWordSearchRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<KeyWordSearchDto> findKeyWordList(int size) {
        return queryFactory.select(Projections.constructor(KeyWordSearchDto.class, keyWordSearchJpaEntity.keyWord, keyWordSearchJpaEntity.frequency, keyWordSearchJpaEntity.targetDate))
                .from(keyWordSearchJpaEntity)
                .where(keyWordSearchJpaEntity.frequency.gt(0)
                        .and(keyWordSearchJpaEntity.targetDate.eq(LocalDate.now())))
                .orderBy(keyWordSearchJpaEntity.frequency.desc())
                .limit(size)
                .fetch();
    }
}

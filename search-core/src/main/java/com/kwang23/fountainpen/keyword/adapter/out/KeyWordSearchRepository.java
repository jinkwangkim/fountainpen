package com.kwang23.fountainpen.keyword.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;

public interface KeyWordSearchRepository extends JpaRepository<KeyWordSearchJpaEntity, Long>, KeyWordSearchRepositoryCustom {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="3000")})
    @Query("select kws from KeyWordSearchJpaEntity kws where kws.keyWord = :keyWord and kws.targetDate = :targetDate")
    KeyWordSearchJpaEntity findByKeyWordAndTargetDateForUpdate(@Param("keyWord") String keyWord, @Param("targetDate") LocalDate targetDate);

    KeyWordSearchJpaEntity findByKeyWordAndTargetDate(String keyWord, LocalDate now);
}

package com.kwang23.fountainpen.domain.searchword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.time.LocalDate;

public interface KeyWordSearchRepository extends JpaRepository<KeyWordSearch, Long>, KeyWordSearchRepositoryCustom {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select kws from KeyWordSearch kws where kws.keyWord = :keyWord and kws.targetDate = :targetDate")
    KeyWordSearch findByKeyWordAndTargetDateForUpdate(@Param("keyWord") String keyWord, @Param("targetDate") LocalDate targetDate);

    KeyWordSearch findByKeyWordAndTargetDate(String keyWord, LocalDate now);
}

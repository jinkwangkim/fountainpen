package com.kwang23.fountainpen.keyword.adapter.in;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchDto;
import com.kwang23.fountainpen.keyword.application.port.in.KeyWordSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeyWordSearchApiController {
    private final KeyWordSearchService keyWordSearchService;
    private final KeyWordSearchPort keyWordSearchRedisPort;

    /**
     * 당일 기준 인기 검색어 조회 API
     *
     * @param size 검색 결과 건수 (1 ~ 20, default : 10)
     * @return 검색어 조회 결과
     */
    @GetMapping("/v1/search/word")
    public List<KeyWordSearchDto> getSearchWordList(@RequestParam(required = false, defaultValue = "10") int size) {
        if (size < 1 || size > 50) throw new IllegalArgumentException("size 는 1 이상 20 이하의 값입니다.");
        return keyWordSearchService.getSearchWordList(size);
    }

    @GetMapping("/v1/redis/search/word")
    public List<KeyWordSearchDto> getRedisSearchWordList(@RequestParam(required = false, defaultValue = "10") int size) {
        if (size < 1 || size > 50) throw new IllegalArgumentException("size 는 1 이상 20 이하의 값입니다.");
        return keyWordSearchRedisPort.getTopTenKeyWord(size);
    }
}

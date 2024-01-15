package com.kwang23.fountainpen.keyword.adapter.out.redis;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchDto;
import com.kwang23.fountainpen.keyword.application.port.out.KeyWordSearchRedisPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class KeyWordSearchRedisService implements KeyWordSearchRedisPort {
    private static final String CACHE_KEY_PREFIX = "keyword:";
    private final RedisTemplate<String, String> keyWordRedisTemplate;

    public List<KeyWordSearchDto> getTopTenKeyWord(int size) {
        ZSetOperations<String, String> ops = keyWordRedisTemplate.opsForZSet();
        LocalDate now = LocalDate.now();
        String key = CACHE_KEY_PREFIX + now;
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ops.reverseRangeWithScores(key, 0, size);
        return typedTuples.stream()
                .map(t -> new KeyWordSearchDto(t.getValue(), t.getScore().longValue(), now))
                .collect(Collectors.toList());
    }
}
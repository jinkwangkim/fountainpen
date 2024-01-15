package com.kwang23.fountainpen.keyword.adapter.out.redis;

import com.kwang23.fountainpen.keyword.adapter.in.KeyWordSearchDto;
import com.kwang23.fountainpen.keyword.application.port.out.AddKeyWordPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class KeyWordStatiscsRedisService implements AddKeyWordPort {
    private static final String CACHE_KEY_PREFIX = "keyword:";
    private final RedisTemplate<String, String> keyWordRedisTemplate;

    public void addKeyWord(String keyWord) {
        ZSetOperations<String, String> ops = keyWordRedisTemplate.opsForZSet();
        LocalDate now = LocalDate.now();
        String key = CACHE_KEY_PREFIX + now;
        Boolean absent = ops.addIfAbsent(key, keyWord, 1);
        if(!absent)
            ops.incrementScore(key, keyWord, 1);
        LocalDate tomorrow = now.plus(1, ChronoUnit.DAYS);
        ops.getOperations().expireAt(key, Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}

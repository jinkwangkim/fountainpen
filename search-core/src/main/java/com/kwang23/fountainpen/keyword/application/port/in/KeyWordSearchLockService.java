package com.kwang23.fountainpen.keyword.application.port.in;

import com.kwang23.fountainpen.infra.lock.DistributedLock;
import com.kwang23.fountainpen.infra.lock.GlobalLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeyWordSearchLockService {
    private final GlobalLockService globalLockService;

    private final KeyWordSearchService keyWordSearchService;

    public void addKeyWordWithDistributedLock(String keyWord) {
        try(DistributedLock addKeyWordLock = globalLockService.getLock("addKeyWordLock:"+keyWord, 50, 3000)) {
            if (!addKeyWordLock.tryLock()) {
                throw new RuntimeException("lock fail");
            }
            keyWordSearchService.addKeyWordNoDbLock(keyWord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

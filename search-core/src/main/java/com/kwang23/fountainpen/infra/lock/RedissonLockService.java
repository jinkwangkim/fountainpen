package com.kwang23.fountainpen.infra.lock;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedissonLockService implements GlobalLockService {
    private final RedissonClient redissonClient;

    public DistributedLock getLock(String lockName, long waitTimeOut, long leaseTime) {
        RLock lock = redissonClient.getLock(lockName);
        return new RLockWrapper(lock, waitTimeOut, leaseTime, 0);
    }
}

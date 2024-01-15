package com.kwang23.fountainpen.infra.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class RLockWrapper implements DistributedLock {
    private final RLock rLock;
    private final long waitTimeOut;
    private final long leaseTime;
    private final long id;
    @Override
    public void close() {
        if(rLock != null && rLock.isHeldByCurrentThread())
            rLock.unlock();
    }

    public boolean tryLock() {
        try {
            return rLock.tryLock(waitTimeOut, leaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

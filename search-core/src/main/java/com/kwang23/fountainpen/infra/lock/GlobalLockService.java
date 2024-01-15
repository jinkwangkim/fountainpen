package com.kwang23.fountainpen.infra.lock;

public interface GlobalLockService {
    DistributedLock getLock(String lockName, long waitTimeOut, long leaseTime);
}

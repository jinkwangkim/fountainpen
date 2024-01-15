package com.kwang23.fountainpen.infra.lock;

public interface DistributedLock extends AutoCloseable {
    boolean tryLock();
}

package com.kwang23.fountainpen.infra.utils;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionSynchronizationFunc {
    private Runnable afterCommitRunnable;
    public static TransactionSynchronizationFunc builer() {
        return new TransactionSynchronizationFunc();
    }

    public TransactionSynchronizationFunc afterCommit(Runnable runnable) {
        this.afterCommitRunnable = runnable;
        return this;
    }

    public void regist() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
            @Override
            public void afterCommit() {
                afterCommitRunnable.run();
            }
        });
    }

}

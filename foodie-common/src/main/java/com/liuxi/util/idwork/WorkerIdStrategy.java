package com.liuxi.util.idwork;

public interface WorkerIdStrategy {
    void initialize();

    long availableWorkerId();

    void release();
}

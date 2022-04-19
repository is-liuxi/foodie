package com.liuxi.util.idwork;

public interface RandomCodeStrategy {
    void init();

    int prefix();

    int next();

    void release();
}

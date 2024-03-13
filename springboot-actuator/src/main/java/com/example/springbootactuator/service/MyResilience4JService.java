package com.example.springbootactuator.service;

import io.github.resilience4j.retry.annotation.Retry;

public interface MyResilience4JService {

    String retry(int code) throws Exception;


    /**
     * 当重试达到上限后还是失败，则作为异常回调方法
     * @param th
     * @param code
     * @return
     */
    String recover(Throwable th);

}

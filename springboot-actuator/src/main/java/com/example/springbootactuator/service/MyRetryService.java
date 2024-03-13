package com.example.springbootactuator.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface MyRetryService {

    /**
     * retryable注解表示该方法需要重试
     * value：出现该指定异常后，进行重试
     * maxAttempts：重试次数上限，这里指定为3次
     * backoff：重试策略，这里指定200ms间隔一次
     * @param code
     * @return
     * @throws Exception
     */
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(200))
    String retry(int code) throws Exception;


    /**
     * 当重试达到上限后还是失败，则作为异常回调方法
     * @param th
     * @param code
     * @return
     */
    @Recover
    String recover(Throwable th, int code);

}

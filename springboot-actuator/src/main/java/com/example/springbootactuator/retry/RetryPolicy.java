package com.example.springbootactuator.retry;

import lombok.Data;

import java.time.Duration;

@Data
public class RetryPolicy {

    /**
     * 重试的上限次数
     */
    int maxAttempts;

    Duration delay;

}

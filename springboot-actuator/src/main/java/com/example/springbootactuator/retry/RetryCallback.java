package com.example.springbootactuator.retry;

public interface RetryCallback<T> {

    T call();

}

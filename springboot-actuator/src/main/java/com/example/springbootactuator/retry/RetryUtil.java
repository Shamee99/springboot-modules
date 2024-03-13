package com.example.springbootactuator.retry;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class RetryUtil {


    /**
     * 重试方法
     * @param invokeFunc   原方法调用
     * @param maxAttempts  重试次数上限
     * @param deplay   重试的间隔时间
     * @param timeUnit   重试的间隔时间单位
     * @param faultFunc   如果超过重试上限次数，那么会执行该错误回调方法
     * @return
     * @param <T>
     */
    public static <T> T retry(Supplier<T> invokeFunc, int maxAttempts, long deplay, TimeUnit timeUnit, Function<Throwable, T> faultFunc) {
        AtomicInteger retryTimes = new AtomicInteger(0);
        for(;;) {
            try{
                return invokeFunc.get();
            } catch (Throwable th) {
                if(retryTimes.get() > maxAttempts){
                    log.error("重试次数超过{}次，进入失败回调", retryTimes.get());
                    return faultFunc.apply(th);
                }
                ThreadUtil.sleep(deplay, timeUnit);
                retryTimes.getAndAdd(1);
            }
        }
    }

}

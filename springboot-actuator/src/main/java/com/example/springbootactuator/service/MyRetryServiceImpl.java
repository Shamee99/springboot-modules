package com.example.springbootactuator.service;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyRetryServiceImpl implements MyResilience4JService {

    @Override
    public String retry(int code) throws Exception {
        log.info("请求retry接口");
        String result = HttpUtil.get("http://localhost:8080/http_test");
        if(code != 200){
            throw new Exception("接口请求异常");
        }

        return result;
    }

    @Override
    public String recover(Throwable th) {
        log.error("回调方法执行！！！！");
        return "异常信息:" + th.getMessage();
    }
}

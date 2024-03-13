package com.example.springbootactuator.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.springbootactuator.retry.RetryUtil;
import com.example.springbootactuator.service.MyResilience4JService;
import com.example.springbootactuator.service.MyRetryService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class HelloWorldController {

    @GetMapping("say")
    public String sqy(){
        return "hello world, spring boot actuator";
    }

    @GetMapping("http_test")
    public String getHttpTest(){
        return "接口请求成功，返回：OK";
    }

    private static volatile CloseableHttpClient HTTP_CLIENT = null;
    static {
        if(HTTP_CLIENT == null){
            synchronized (HelloWorldController.class) {
                if(HTTP_CLIENT == null){
                    HTTP_CLIENT = HttpClients.custom()
                            // 设置重试策略
//                            .setRetryStrategy(new DefaultHttpRequestRetryStrategy(3, TimeValue.NEG_ONE_SECOND))
                            // 自定义重试策略
                            .setRetryStrategy(new CustomRetryStrategy())
                            .build();
                }
            }
        }
    }

    public static class CustomRetryStrategy implements HttpRequestRetryStrategy {

        @Override
        public boolean retryRequest(HttpRequest httpRequest, IOException e, int executeCount, HttpContext httpContext) {
            return false;
        }

        @Override
        public boolean retryRequest(HttpResponse httpResponse, int executeCount, HttpContext httpContext) {
            System.out.println("进入重试策略");
            if(executeCount > 3){
                System.out.println("重试超过3次，终止重试");
                return false;
            }

            if(httpResponse.getCode() != 200){
                System.out.println("http状态码不等于200，进行重试");
                return true;
            }

            // 其他情况，不重试
            return false;
        }

        @Override
        public TimeValue getRetryInterval(HttpResponse httpResponse, int executeCount, HttpContext httpContext) {
            return null;
        }
    }


    /**
     * 循环重试
     * @return
     */
    @GetMapping("retry_demo_loop")
    public String retry_demo_loop(){
        // 重试上限次数为3次
        int maxRetryTime = 3;
        String result = null;
        // 接口循环请求
        for (int i = 1; i <= maxRetryTime; i++) {
            try {
                // 模拟请求接口
                result = HttpUtil.get("http://localhost:8080/http_test");
                // 模拟一次请求失败
                if(i == 1){
                    int co = i / 0;
                }
                // 请求成功，跳出循环
                break;
            } catch (Exception e) {
                log.error("接口请求异常，进行第{}次重试", i);
                result = "接口请求失败，请联系管理员";
            }
        }
        return result;
    }


    @GetMapping("retry_demo_rec")
    public String retry_demo_rec(){
        // 重试上限次数为3次
        int maxRetryTime = 3;
        return retryRequest(maxRetryTime);
    }

    /**
     * 递归方法
     * @param maxRetryTime
     * @return
     */
    private String retryRequest(int maxRetryTime){
        if (maxRetryTime <= 0) {
            return "接口请求失败，请联系管理员";
        }

        int retryTime = 0;
        try {
            // 模拟请求接口
            String result = HttpUtil.get("http://localhost:8080/http_test");
            // 模拟一次请求失败
            if(maxRetryTime == 3){
                int co = 1 / 0;
            }
            return result;
        } catch (Exception e) {
            // 处理异常
            log.error("接口请求异常，进行第{}次重试", ++retryTime);
            return retryRequest(maxRetryTime - 1);
        }
    }

    @Autowired
    private MyResilience4JService myRetryService;


    /**
     * 当请求code参数为200时，直接成功
     * 当code参数!=200时，会出发重试
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("retry_demo_spring_retry")
    @Retry(name = "retry_demo", fallbackMethod = "recover")
    public String retry_demo_spring_retry(Integer code) throws Exception {
        return myRetryService.retry(code);
    }

    public String recover(Throwable th) {
        log.error("回调方法执行！！！！");
        return "异常信息：" + th.getMessage();
    }


    @GetMapping("retry_demo_httpclient")
    public String retry_demo_httpclient(Integer code) throws Exception {
        return httpclientRetry(code);
    }

    private String httpclientRetry(int code) throws Exception {
        log.info("请求retry接口");
        HttpGet request = new HttpGet("http://localhost:8080/http_test1");
        CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(request);
        String result = IoUtil.read(httpResponse.getEntity().getContent()).toString();
        if(code != 200){
            throw new Exception("接口请求异常");
        }

        return result;
    }

    @GetMapping("retry_demo_custom")
    public String retry_demo_custom(Integer code)  {
        return RetryUtil.retry(() -> {
            String result = null;
            try {
                result = customRetry(code);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return result;
        }, 3, 1000, TimeUnit.MILLISECONDS, Throwable::getMessage);
    }

    private String customRetry(int code) throws Exception {
        log.info("请求customRetry接口");
        String result = HttpUtil.get("http://localhost:8080/http_test");
        if(code != 200){
            throw new Exception("接口请求异常");
        }

        return result;
    }
}

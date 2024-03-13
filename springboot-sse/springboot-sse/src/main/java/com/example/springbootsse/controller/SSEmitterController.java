package com.example.springbootsse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;


@RestController
@RequestMapping("/sse")
public class SSEmitterController {

    @GetMapping("/stream")
    public SseEmitter stream() {
        // 3S超时
        SseEmitter emitter = new SseEmitter(3000L);

        // 注册回调函数，处理服务器向客户端推送的消息
        emitter.onCompletion(() -> {
            System.out.println("Connection completed");
            // 在连接完成时执行一些清理工作
        });

        emitter.onTimeout(() -> {
            System.out.println("Connection timeout");
            // 在连接超时时执行一些处理
            emitter.complete();
        });

        // 在后台线程中模拟实时数据
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    emitter.send(SseEmitter.event().name("message").data("[" + new Date() + "] Data #" + i));
                    Thread.sleep(1000);
                }
                emitter.complete(); // 数据发送完成后，关闭连接
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e); // 发生错误时，关闭连接并报错
            }
        }).start();

        return emitter;
    }

}

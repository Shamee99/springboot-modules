package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.config.MqProducerCallBack;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("push/message")
public class PushMessageController {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MqProducerCallBack mqProducerCallBack;


    @GetMapping("test")
    public String sendMessage() {
        // correlationData：对象内部只有一个 id 属性，用来表示当前消息的唯一性。
        CorrelationData correlationData = new CorrelationData("id_" + System.currentTimeMillis());
        // 消息确认和返回回调
        rabbitTemplate.setConfirmCallback(mqProducerCallBack);
        rabbitTemplate.setReturnsCallback(mqProducerCallBack);
        // 消息发送
        rabbitTemplate.convertAndSend("my-queue", "hello world", message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        }, correlationData);
        return "publisher success...";
    }

}

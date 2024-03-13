package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.config.ExchangeQueueConstant;
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
        for (int i = 0; i < 5; i++) {
            CorrelationData correlationData = new CorrelationData("id_" + System.currentTimeMillis() + "");
            rabbitTemplate.setConfirmCallback(mqProducerCallBack);
            rabbitTemplate.setReturnsCallback(mqProducerCallBack);
            rabbitTemplate.convertAndSend(ExchangeQueueConstant.TEST_WORK_QUEUE, "hello world", message -> {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            },correlationData);
        }
        return "———————————Work—————————Work—————————————";
    }

}

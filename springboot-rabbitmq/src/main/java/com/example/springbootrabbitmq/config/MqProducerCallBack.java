package com.example.springbootrabbitmq.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MqProducerCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        /**
         * correlationData：对象内部只有一个 id 属性，用来表示当前消息的唯一性。
         * ack：消息投递到broker 的状态，true表示成功。
         * cause：表示投递失败的原因。
         */
        if (!ack) {
            System.err.println("消息发送异常!");
        } else {
            System.out.println("发送者已经收到确认，correlationData="+correlationData.getId()+" ,ack="+ack+", cause="+cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        System.err.println("ReturnedMessage: " + returnedMessage);
        System.out.println("退回的消息是："+new String(returnedMessage.getMessage().getBody()));
        System.out.println("退回的replyCode是："+returnedMessage.getReplyCode());
        System.out.println("退回的replyText是："+returnedMessage.getReplyText());
        System.out.println("退回的exchange是："+returnedMessage.getExchange());
        System.out.println("退回的routingKey是："+returnedMessage.getRoutingKey());
    }
}
package com.example.springbootrabbitmq.config;

import cn.hutool.json.JSONUtil;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MqProducerCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    /**
     * correlationData：对象内部只有一个 id 属性，用来表示当前消息的唯一性。
     * ack：消息投递到broker 的状态，true成功，false失败。
     * cause：投递失败的原因。
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            System.err.println("消息ID=" + correlationData.getId() + "投递失败，失败原因：" + cause);
        } else {
            System.out.println("消息投递收到确认，correlationData=" + correlationData.getId());
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        System.out.println("返回消息结果：" + JSONUtil.toJsonStr(returnedMessage));
    }

}
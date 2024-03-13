package com.example.springbootrabbitmq.consumer;

import com.example.springbootrabbitmq.config.ExchangeQueueConstant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PushMessageConsumer {


    @RabbitListener(queuesToDeclare = @Queue(value = ExchangeQueueConstant.TEST_WORK_QUEUE))
    @RabbitHandler
    public void testWork1(String msg, Channel channel, Message message) throws IOException {
        try {
            System.out.println("小富收到消息：" + msg);

            /**
             * basicAck：表示成功确认，使用此回执方法后，消息会被rabbitmq broker 删除。
             * void basicAck(long deliveryTag, boolean multiple)
             * deliveryTag：表示消息投递序号，每次消费消息或者消息重新投递后，deliveryTag都会增加。手动消息确认模式下，我们可以对指定deliveryTag的消息进行ack、nack、reject等操作。
             * multiple：是否批量确认，值为 true 则会一次性 ack所有小于当前消息 deliveryTag 的消息。
             * */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("22222222222__________" + message.getMessageProperties().getDeliveryTag());
            System.out.println("3333333333__________" + message.getMessageProperties().getRedelivered());
            //TODO 具体业务
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                System.out.println("消息已重复处理失败,拒绝再次接收！");
                // 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列
                /**
                 * basicReject：拒绝消息，与basicNack区别在于不能进行批量操作，其他用法很相似。
                 * deliveryTag：表示消息投递序号。
                 * requeue：值为 true 消息将重新入队列。
                 */
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                System.out.println("消息即将再次返回队列处理！");
                // requeue为是否重新回到队列，true重新入队
                /**
                 * deliveryTag：表示消息投递序号。
                 * multiple：是否批量确认。
                 * requeue：值为 true 消息将重新入队列。
                 */
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

}

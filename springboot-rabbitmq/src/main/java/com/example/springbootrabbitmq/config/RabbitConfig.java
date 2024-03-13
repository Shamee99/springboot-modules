package com.example.springbootrabbitmq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Bean
    public Queue myQueue() {
        return new Queue("my-queue");
    }

    @Bean
    public Exchange myExchange() {
        return new DirectExchange("my-exchange");
    }


}

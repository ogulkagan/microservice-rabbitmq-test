package com.account.account.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.binding.account.routing.key}")
    private String accountKey;

    @Value("${rabbitmq.binding.account.update.routing.key}")
    private String accountUpdateKey;

    @Value("${rabbitmq.queue.account.name}")
    private String queue;

    @Value("${rabbitmq.queue.account.update.name}")
    private String queueAccountUpdate;

    @Value("${rabbitmq.exchange.account.name}")
    private String exchange;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue accountQueue(){
        return new Queue(queue);
    }

    @Bean
    public Queue accountUpdateQueue(){
        return new Queue(queueAccountUpdate);
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2MessageConverter());
        return template;
    }

    @Bean
    public Binding accountbinding(DirectExchange directExchange,
                                  Queue accountQueue){
        return BindingBuilder
                .bind(accountQueue())
                .to(directExchange())
                .with(accountKey);
    }

    @Bean
    public Binding accountUpdateBinding(DirectExchange directExchange,
                                        Queue accountUpdateQueue){
        return BindingBuilder
                .bind(accountUpdateQueue())
                .to(directExchange())
                .with(accountUpdateKey);
    }

    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
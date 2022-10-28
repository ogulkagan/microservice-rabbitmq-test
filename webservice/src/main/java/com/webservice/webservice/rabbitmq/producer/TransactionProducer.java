package com.webservice.webservice.rabbitmq.producer;

import com.webservice.webservice.dto.TransactionDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange directExchange;

    @Value("${rabbitmq.binding.transaction.routing.key}")
    private String transactionRoutingKey;

    public Integer createTransaction(TransactionDto transactionDto){
        Object temp = template.convertSendAndReceive(
            directExchange.getName(),
            transactionRoutingKey,
            transactionDto);
            
        return (Integer) temp;
    }

}

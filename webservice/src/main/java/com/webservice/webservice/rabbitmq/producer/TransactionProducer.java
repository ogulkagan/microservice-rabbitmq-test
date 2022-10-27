package com.webservice.webservice.rabbitmq.producer;

import com.webservice.webservice.dto.AccountTransactionDto;
import com.webservice.webservice.dto.TransactionDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange directExchange;

    @Value("${rabbitmq.binding.transaction.routing.key}")
    private String transactionRoutingKey;

    public int createTransaction(TransactionDto transactionDto){
        return template.convertSendAndReceiveAsType(
                directExchange.getName(),
                transactionRoutingKey,
                transactionDto,
                new ParameterizedTypeReference<>() {});
    }

}

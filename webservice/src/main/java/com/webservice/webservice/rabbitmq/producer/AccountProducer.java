package com.webservice.webservice.rabbitmq.producer;

import com.webservice.webservice.dto.AccountDto;
import com.webservice.webservice.dto.AccountTransactionDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
public class AccountProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange directExchange;

    @Value("${rabbitmq.binding.account.routing.key}")
    private String accountRoutingKey;

    @Value("${rabbitmq.binding.account.update.routing.key}")
    private String accountUpdateRoutingKey;

    public int createAccount(AccountDto accountDto){
        return template.convertSendAndReceiveAsType(
                directExchange.getName(),
                accountRoutingKey,
                accountDto,
                new ParameterizedTypeReference<>() {});
    }

    public double updateAccountBalance(AccountTransactionDto accountTransactionDto){
        return template.convertSendAndReceiveAsType(
                directExchange.getName(),
                accountUpdateRoutingKey,
                accountTransactionDto,
                new ParameterizedTypeReference<>() {});
    }

}

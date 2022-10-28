package com.webservice.webservice.rabbitmq.producer;

import com.webservice.webservice.dto.AccountDto;
import com.webservice.webservice.dto.AccountTransactionDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public Integer createAccount(AccountDto accountDto){
        Object temp = template.convertSendAndReceive(
                directExchange.getName(),
                accountRoutingKey,
                accountDto);
        return (Integer) temp;
    }

    public Double updateAccountBalance(AccountTransactionDto accountTransactionDto){
        Object temp =  template.convertSendAndReceive(
                directExchange.getName(),
                accountUpdateRoutingKey,
                accountTransactionDto);

        return (Double) temp;
    }

}

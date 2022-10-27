package com.account.account.consumer;

import com.account.account.dto.AccountDto;
import com.account.account.dto.AccountTransactionDto;
import com.account.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountConsumer {

    @Autowired
    private AccountService accountService;

    private Logger LOGGER = LoggerFactory.getLogger(AccountConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.account.name}")
    public int consume(AccountDto accountDto){
        LOGGER.info(String.format("Order event received in account service => %s", accountDto.toString()));
        //return 1;
        return accountService.createAccount(accountDto);
    }

    @RabbitListener(queues = "${rabbitmq.queue.account.update.name}")
    public double consume(AccountTransactionDto accountTransactionDto){
        LOGGER.info(String.format("Order event received in account service => %s", accountTransactionDto.toString()));

        return accountService.updateAccountBalance(accountTransactionDto);
    }

}

package com.transaction.transaction.consumer;

import com.transaction.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.transaction.transaction.dto.TransactionDto;
@Component
public class TransactionConsumer {
    @Autowired
    private TransactionService transactionService;

    private Logger LOGGER = LoggerFactory.getLogger(TransactionConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.transaction.name}")
    public int consume(TransactionDto transactionDto){
        LOGGER.info(String.format("Order event received in transaction service => %s", transactionDto.toString()));

        return transactionService.createTransaction(transactionDto);
    }

}

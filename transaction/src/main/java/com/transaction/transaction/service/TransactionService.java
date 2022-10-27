package com.transaction.transaction.service;

import com.transaction.transaction.dbo.TransactionDBO;
import com.transaction.transaction.dto.TransactionDto;
import com.transaction.transaction.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    public int createTransaction(TransactionDto transactionDto){
        TransactionDBO transaction = TransactionDBO.builder()
                .currency(transactionDto.getCurrency())
                .account_id(transactionDto.getAccount_id())
                .amount(transactionDto.getAmount())
                .currency(transactionDto.getCurrency())
                .description(transactionDto.getDescription())
                .direction(transactionDto.getDirection())
                .build();

        transactionMapper.insert(transaction);

        return transaction.getId();
    }
    
}

package com.webservice.webservice.service;

import com.webservice.webservice.dto.AccountResponseDto;
import com.webservice.webservice.dto.AccountTransactionDto;
import com.webservice.webservice.dto.TransactionDto;
import com.webservice.webservice.dto.TransactionResponseDto;
import com.webservice.webservice.mybatis.dbo.AccountDetailDBO;
import com.webservice.webservice.mybatis.dbo.TransactionDBO;
import com.webservice.webservice.mybatis.mapper.AccountMapper;
import com.webservice.webservice.mybatis.mapper.TransactionMapper;
import com.webservice.webservice.rabbitmq.producer.AccountProducer;
import com.webservice.webservice.rabbitmq.producer.TransactionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionProducer transactionProducer;

    @Autowired
    private AccountProducer accountProducer;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    public TransactionResponseDto isTransactionValid(TransactionDto transactionDto){
        String[] validCurrencies = {"EUR","SEK","GBP","USD"};
        if(!Arrays.asList(validCurrencies).contains(transactionDto.getCurrency())){
            return TransactionResponseDto
                    .builder()
                    .message("Invalid Currency")
                    .build();
        }

        String[] validDirections = {"IN","OUT"};
        if(!Arrays.asList(validDirections).contains(transactionDto.getDirection())){
            return TransactionResponseDto
                    .builder()
                    .message("Invalid Direction")
                    .build();
        }

        if(transactionDto.getAmount() < 0.0){
            return TransactionResponseDto
                    .builder()
                    .message("Invalid Amount")
                    .build();
        }

        List<AccountDetailDBO> accountDetailDBO = accountMapper.findAccountDetailsWithIdAndCurrency(AccountDetailDBO
                .builder()
                .account_id(transactionDto.getAccount_id())
                .currency(transactionDto.getCurrency())
                .build());
        if(accountDetailDBO.size() == 0){
            return TransactionResponseDto
                    .builder()
                    .message("Invalid Account with Currency")
                    .build();
        }

        if(transactionDto.getDirection().equals("OUT") && (accountDetailDBO.get(0).getAmount() - transactionDto.getAmount() ) < 0.0){
            return TransactionResponseDto
                    .builder()
                    .message("Insufficient funds")
                    .build();
        }


        return TransactionResponseDto
                .builder()
                .message("")
                .build();
    }

    public TransactionResponseDto createTransaction(TransactionDto transactionDto) {
        AccountTransactionDto accountTransactionDto = AccountTransactionDto.builder().build();
        //Update the balance!
        List<AccountDetailDBO> accountDetailDBO = accountMapper.findAccountDetailsWithIdAndCurrency(AccountDetailDBO
                .builder()
                        .account_id(transactionDto.getAccount_id())
                        .currency(transactionDto.getCurrency())
                .build());
        if (transactionDto.getDirection().equals("IN")) {
            accountTransactionDto.setAmount(accountDetailDBO.get(0).getAmount() + transactionDto.getAmount());
        } else {
            accountTransactionDto.setAmount(accountDetailDBO.get(0).getAmount() - transactionDto.getAmount());
        }
        accountTransactionDto.setId(accountDetailDBO.get(0).getId());
        accountProducer.updateAccountBalance(accountTransactionDto);

        //Create the transaction
        TransactionResponseDto responseDto = getTransaction(transactionProducer.createTransaction(transactionDto));
        responseDto.setBalance(accountTransactionDto.getAmount());
        return responseDto;
    }

    public TransactionResponseDto getTransaction(int transactionId) {
        //We'll get the transaction
        TransactionDBO transaction = transactionMapper.findTransaction(transactionId);

        if (transaction == null) {
            return TransactionResponseDto.builder()
                    .message("Transaction not found!")
                    .build();
        }

        return TransactionResponseDto.builder()
                .description(transaction.getDescription())
                .account_id(transaction.getAccount_id())
                .amount(transaction.getAmount())
                .direction(transaction.getDirection())
                .id(transactionId)
                .build();
    }

    public List<TransactionResponseDto> getAllTransaction(int accountId) {
        //We'll get the transaction
        List<TransactionDBO> transactionList = transactionMapper.findAllTransactions(accountId);

        ArrayList<TransactionResponseDto> responseDtos = new ArrayList<>();
        if (transactionList == null) {
            responseDtos.add(TransactionResponseDto.builder()
                    .message("Invalid account")
                    .build());
            return responseDtos;
        }

        for (TransactionDBO transaction : transactionList) {
            responseDtos.add(TransactionResponseDto.builder()
                    .description(transaction.getDescription())
                    .account_id(transaction.getAccount_id())
                    .amount(transaction.getAmount())
                    .direction(transaction.getDirection())
                    .id(transaction.getId())
                    .currency(transaction.getCurrency())
                    .build());

        }

        return responseDtos;
    }


}

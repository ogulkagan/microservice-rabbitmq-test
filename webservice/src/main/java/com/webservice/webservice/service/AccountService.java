package com.webservice.webservice.service;

import com.webservice.webservice.dto.AccountDetailDto;
import com.webservice.webservice.dto.AccountDto;
import com.webservice.webservice.dto.AccountResponseDto;
import com.webservice.webservice.mybatis.dbo.AccountDBO;
import com.webservice.webservice.mybatis.dbo.AccountDetailDBO;
import com.webservice.webservice.mybatis.mapper.AccountMapper;
import com.webservice.webservice.rabbitmq.producer.AccountProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountProducer accountProducer;

    @Autowired
    private AccountMapper accountMapper;

    public AccountResponseDto createAccount(AccountDto accountDto){
        int accountId = accountProducer.createAccount(accountDto);
        return getAccountWithId(accountId);
    }

    public AccountResponseDto getAccountWithId(int accountId){
        AccountDBO accountDBO = accountMapper.findAccountWithId(accountId);

        if(accountDBO == null){
            return AccountResponseDto.builder()
                    .message("Account not found")
                    .build();
        }

        ArrayList<AccountDetailDto> accountDetailDtos = new ArrayList<>();
        List<AccountDetailDBO> accountDetailDBOS = accountMapper.findAccountDetailsWithId(accountId);
        for(AccountDetailDBO accountDetailDBO : accountDetailDBOS){
            accountDetailDtos.add(AccountDetailDto.builder()
                    .amount(accountDetailDBO.getAmount())
                    .currency(accountDetailDBO.getCurrency())
                    .build());
        }

        return AccountResponseDto.builder()
                .account_id(accountId)
                .customer_id(accountDBO.getCustomer_id())
                .currencies(accountDetailDtos)
                .build();
    }


}

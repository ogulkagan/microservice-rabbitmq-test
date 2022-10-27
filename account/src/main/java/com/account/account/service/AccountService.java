package com.account.account.service;

import com.account.account.dbo.AccountDBO;
import com.account.account.dbo.AccountDetailDBO;
import com.account.account.dto.AccountDto;
import com.account.account.dto.AccountTransactionDto;
import com.account.account.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public int createAccount(AccountDto accountDto){
        //Create the account
        AccountDBO account = AccountDBO.builder().customer_id(accountDto.getCustomer_id()).build();
        accountMapper.insert(account);

        //Create the account details
        createAccountDetail(accountDto,account);

        return account.getId();
    }

    public void createAccountDetail(AccountDto accountDto, AccountDBO account){
        for (String currency : accountDto.getCurrencies() )
            accountMapper.insertDetails(AccountDetailDBO.builder().account_id(account.getId()).amount(0).currency(currency).build());
    }

    public double updateAccountBalance(AccountTransactionDto accountTransactionDto){
        accountMapper.updateAccountBalance(accountTransactionDto.getAmount(), accountTransactionDto.getId());
        return accountTransactionDto.getAmount();
    }


}

package com.webservice.webservice.controller;

import com.webservice.webservice.dto.AccountDto;
import com.webservice.webservice.dto.AccountResponseDto;
import com.webservice.webservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    private ValidatorFactory factory;

    private Validator validator;

    public AccountController(){
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @GetMapping("/test")
    ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @PostMapping
    ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountDto account) {
        if(this.validator.validate(account).size() != 0){
            return ResponseEntity.ok(AccountResponseDto
                    .builder()
                            .message(validator.validate(account).iterator().next().getMessage())
                    .build());
        }

        String[] values = {"EUR","SEK","GBP","USD"};
        for(String currency : account.getCurrencies()){
            if(!Arrays.asList(values).contains(currency)){
                return ResponseEntity.ok(AccountResponseDto
                        .builder()
                        .message("Invalid Currency")
                        .build());
            }
        }

        //We'll send the message to the queue
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/{accountId}")
    ResponseEntity<AccountResponseDto> getAccount(@PathVariable("accountId") int accountId) {
        //We'll get the account information
        return ResponseEntity.ok(accountService.getAccountWithId(accountId));
    }

}

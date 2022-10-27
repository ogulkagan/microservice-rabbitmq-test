package com.webservice.webservice.controller;

import com.webservice.webservice.dto.TransactionDto;
import com.webservice.webservice.dto.TransactionResponseDto;
import com.webservice.webservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private ValidatorFactory factory;

    private Validator validator;

    public TransactionController(){
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @GetMapping("/test")
    ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @PostMapping
    ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        if(this.validator.validate(transactionDto).size() != 0){
            return ResponseEntity.ok(TransactionResponseDto
                    .builder()
                    .message(validator.validate(transactionDto).iterator().next().getMessage())
                    .build());
        }
        TransactionResponseDto transactionResponseDto = transactionService.isTransactionValid(transactionDto);
        if(!transactionResponseDto.getMessage().isEmpty()){
            return ResponseEntity.ok(transactionResponseDto);
        }

        //We'll send the message to the queue
        return ResponseEntity.ok(transactionService.createTransaction(transactionDto));
    }

    @GetMapping("/{accountId}")
    ResponseEntity<List<TransactionResponseDto>> getTransaction(@PathVariable("accountId") int accountId) {
        //We'll get the transaction information
        return ResponseEntity.ok(transactionService.getAllTransaction(accountId));
    }


}

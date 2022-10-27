package com.webservice.webservice.controller;

import com.webservice.webservice.dto.AccountDto;
import com.webservice.webservice.dto.AccountResponseDto;
import com.webservice.webservice.dto.TransactionDto;
import com.webservice.webservice.dto.TransactionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionApplicationTest {
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void getTest() {
		ResponseEntity<String> response = testRestTemplate.getForEntity("/transaction/test", String.class);
		assertEquals("test", response.getBody());
	}

	@Test
	public void createTransactionTest() {
		ArrayList<String> currencies = new ArrayList<>();
		currencies.add("GBP");
		AccountDto accountDto = AccountDto
				.builder()
				.country("Estonia")
				.customer_id("1234")
				.currencies(currencies)
				.build();

		HttpEntity<AccountDto> request = new HttpEntity<>(accountDto);
		ResponseEntity<AccountResponseDto> response = testRestTemplate.postForEntity("/account/", request, AccountResponseDto.class);

		assertNotEquals(0, response.getBody().getAccount_id());
		if(response.getBody().getAccount_id() != 0){
			//We'll test funds and create the transaction
			TransactionDto transactionDto = TransactionDto
					.builder()
					.account_id(response.getBody().getAccount_id())
					.amount(10)
					.currency("GBP")
					.description("Test")
					.direction("OUT")
					.build();

			//Invalid currency
			HttpEntity<TransactionDto> requestFund = new HttpEntity<>(transactionDto);
			ResponseEntity<TransactionResponseDto> responseFund = testRestTemplate.postForEntity("/transaction/", requestFund, TransactionResponseDto.class);
			assertEquals("Insufficient funds", responseFund.getBody().getMessage());

			transactionDto.setDirection("IN");
			requestFund = new HttpEntity<>(transactionDto);
			responseFund = testRestTemplate.postForEntity("/transaction/", requestFund, TransactionResponseDto.class);
			assertNotEquals(0, responseFund.getBody().getId());
			assertNull(responseFund.getBody().getMessage());
		}

	}

	@Test
	public void createAccountInvalidDataTest() {
		TransactionDto transactionDto = TransactionDto
				.builder()
				.account_id(1)
				.amount(10)
				.currency("TRY")
				.description("Test")
				.direction("IN")
				.build();

		//Invalid currency
		HttpEntity<TransactionDto> request = new HttpEntity<>(transactionDto);
		ResponseEntity<TransactionResponseDto> response = testRestTemplate.postForEntity("/transaction/", request, TransactionResponseDto.class);
		assertEquals("Invalid Currency", response.getBody().getMessage());

		//Invalid Direction
		transactionDto.setDirection("WRONG!");
		transactionDto.setCurrency("EUR");
		request = new HttpEntity<>(transactionDto);
		response = testRestTemplate.postForEntity("/transaction/", request, TransactionResponseDto.class);
		assertEquals("Invalid Direction", response.getBody().getMessage());

		//Invalid Direction
		transactionDto.setDirection("IN");
		transactionDto.setCurrency("EUR");
		transactionDto.setAmount(-10);
		request = new HttpEntity<>(transactionDto);
		response = testRestTemplate.postForEntity("/transaction/", request, TransactionResponseDto.class);
		assertEquals("Invalid Amount", response.getBody().getMessage());

	}

}

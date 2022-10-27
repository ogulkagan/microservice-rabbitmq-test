package com.webservice.webservice.controller;

import com.webservice.webservice.dto.AccountDto;
import com.webservice.webservice.dto.AccountResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountApplicationTest {
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void getTest() {
		ResponseEntity<String> response = testRestTemplate.getForEntity("/account/test", String.class);
		assertEquals("test", response.getBody());
	}

	@Test
	@Sql("/test.sql")
	public void getAccountTest() {
		ResponseEntity<AccountResponseDto> response = testRestTemplate.getForEntity("/account/100005", AccountResponseDto.class);
		System.out.println(response.getBody().toString());

		assertEquals(100005, response.getBody().getAccount_id());
		assertEquals(0, response.getBody().getCurrencies().get(0).getAmount());
		assertEquals("USD", response.getBody().getCurrencies().get(0).getCurrency());
	}

	@Test
	public void getInvalidAccountTest() {
		ResponseEntity<AccountResponseDto> response = testRestTemplate.getForEntity("/account/100007", AccountResponseDto.class);

		assertEquals("Account not found", response.getBody().getMessage());
	}

	@Test
	public void createAccountTest() {
		ArrayList<String> currencies = new ArrayList<>();
		currencies.add("EUR");
		currencies.add("SEK");

		AccountDto accountDto = AccountDto
				.builder()
				.country("Estonia")
				.customer_id("1234")
				.currencies(currencies)
				.build();

		HttpEntity<AccountDto> request = new HttpEntity<>(accountDto);
		ResponseEntity<AccountResponseDto> response = testRestTemplate.postForEntity("/account/", request, AccountResponseDto.class);

		assertNotEquals(0, response.getBody().getAccount_id());
	}

	@Test
	public void createAccountInvalidDataTest() {
		ArrayList<String> currencies = new ArrayList<>();
		currencies.add("EUR");
		currencies.add("TRY");

		AccountDto accountDto = AccountDto
				.builder()
				.country("Estonia")
				.customer_id("1234")
				.currencies(currencies)
				.build();

		HttpEntity<AccountDto> request = new HttpEntity<>(accountDto);
		ResponseEntity<AccountResponseDto> response = testRestTemplate.postForEntity("/account/", request, AccountResponseDto.class);

		assertEquals("Invalid Currency", response.getBody().getMessage());
	}

}

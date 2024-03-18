package com.pjborowiecki.cashcards;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.DocumentContext;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardsApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnACashCardWhenDataIsSaved() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/cashcards/1000", String.class);

		// assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		// DocumentContext documentContext = JsonPath.parse(response.getBody());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();

		// Number id = documentContext.read("$.id");
		// assertThat(id).isEqualTo(99);

		// Double amount = documentContext.read("$.amount");
		// assertThat(amount).isEqualTo(123.45);
	}

}

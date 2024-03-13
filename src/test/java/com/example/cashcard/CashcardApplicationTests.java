package com.example.cashcard;

import com.example.cashcard.entity.CashCard;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashcardApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;



	@Test
	void shouldReturnACashCardWhenDataIsSaved() {

		ResponseEntity<String> response =
				restTemplate.withBasicAuth("lorenchess", "abc123")
						    .getForEntity("/cashcards/99", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.cashCardId");
		assertThat(id).isNotNull();
		assertThat(id).isEqualTo(99);

		Double amount = documentContext.read("$.amount");
		assertThat(amount).isNotNull();
		assertThat(amount).isEqualTo(123.45);

		String owner = documentContext.read("$.owner");
		assertThat(owner).isNotNull();
		assertThat(owner).isEqualTo("lorenchess");
	}

	@Test
	void shouldNotReturnACashCardWithAnUnknownId() {
		ResponseEntity<String> response =
				restTemplate.withBasicAuth("lorenchess", "abc123")
						    .getForEntity("/cashcards/1000", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isEqualTo("{\"Error message:\":\"CashCard not found for id: 1000\"}");
	}

	@Test
	@DirtiesContext
	void shouldCreateANewCashCardWithLocation() {
		String url = "/cashcards";
		CashCard cashCard = new CashCard(null, 123.45, "lorenchess");
		URI newCashCardLocation = restTemplate.withBasicAuth("lorenchess", "abc123").postForLocation(url, cashCard);
		CashCard retrievedCashCard = restTemplate.withBasicAuth("lorenchess", "abc123").getForObject(newCashCardLocation, CashCard.class);
		assertThat(retrievedCashCard.getAmount()).isEqualTo(cashCard.getAmount());
	}

	@Test
	@DirtiesContext
	void shouldReturnNullLocationIfPassingOwnerNull() {
		String url = "/cashcards";
		CashCard cashCard = new CashCard(null, 123.45, null);
		URI newCashCardLocation = restTemplate.withBasicAuth("lorenchess", "abc123").postForLocation(url, cashCard);
		assertThat(newCashCardLocation).isNull();

	}

	@Test
	@DirtiesContext
	void shouldCreateANewCashCard() {
		CashCard newCashCard = new CashCard(2L, 250.00, "lorenchess");
		ResponseEntity<Void> createResponse = restTemplate.withBasicAuth("lorenchess", "abc123").postForEntity("/cashcards", newCashCard, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI location = createResponse.getHeaders().getLocation();
		ResponseEntity<String> response = restTemplate.withBasicAuth("lorenchess", "abc123").getForEntity(location, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void shouldReturnAllCashCardsWhenListIsRequested() {
		ResponseEntity<String> response =
				restTemplate.withBasicAuth("lorenchess", "abc123").getForEntity("/cashcards", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int cashCardCount = documentContext.read("$.length()");
		assertThat(cashCardCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..cashCardId");
		assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);

		JSONArray amounts = documentContext.read("$..amount");
		assertThat(amounts).containsExactlyInAnyOrder(123.45, 1.0, 150.00);

	}

	@Test
	void shouldReturnAPageOfCashCards() {
		ResponseEntity<String> response =
				restTemplate.withBasicAuth("lorenchess", "abc123").getForEntity("/cashcards?page=0&size=1&sort=amount,desc", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);

		double amount = documentContext.read("$[0].amount");
		assertThat(amount).isEqualTo(150.00);

	}

	@Test
	void shouldReturnASortedPageOfCashCardsWithNoParametersAndUseDefaultValues() {
		ResponseEntity<String> response =
				restTemplate.withBasicAuth("lorenchess", "abc123").getForEntity("/cashcards", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(3);

		JSONArray amounts = documentContext.read("$..amount");
		assertThat(amounts).containsExactly(1.00, 123.45, 150.00);
	}

	@Test
	void shouldNotReturnACashCardWhenUsingBadCredentials() {
		ResponseEntity<String> response =
				restTemplate.withBasicAuth("BAD-USER", "no-pass").getForEntity("/cashcards/99", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

		response = restTemplate
				.withBasicAuth("lorenchess", "BAD-PASSWORD")
				.getForEntity("/cashcards/99", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

		response = restTemplate
				.withBasicAuth("BAD-USER", "abc123")
				.getForEntity("/cashcards/99", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void shouldRejectUsersWhoAreNotCardOwners() {
		ResponseEntity<String> response =
				restTemplate.withBasicAuth("leo", "leo123").getForEntity("/cashcards/99", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	void shouldNotAllowAccessToCashCardsTheyDoNotOwn() {
		ResponseEntity<String> response =
				restTemplate.withBasicAuth("lorenchess", "abc123").getForEntity("/cashcards/102", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DirtiesContext
	void shouldUpdateAnExistingCashCard() {
		CashCard cashCard = new CashCard(null, 19.99, "lorenchess");
		HttpEntity<CashCard> request = new HttpEntity<>(cashCard);
		ResponseEntity<Void> response = restTemplate.withBasicAuth("lorenchess", "abc123")
				.exchange("/cashcards/99", HttpMethod.PUT, request, Void.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> responseEntity =
				restTemplate.withBasicAuth("lorenchess", "abc123").getForEntity("/cashcards/99", String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(responseEntity.getBody());
		Number id = documentContext.read("$.cashCardId");
		Double amount = documentContext.read("$.amount");
		assertThat(id).isEqualTo(99);
		assertThat(amount).isEqualTo(19.99);

	}


}

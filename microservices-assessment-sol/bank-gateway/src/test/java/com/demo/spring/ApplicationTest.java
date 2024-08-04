package com.demo.spring;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"bankserver=http://localhost:${wiremock.server.port}","userserver=http://localhost:${wiremock.server.port}" })
@AutoConfigureWireMock(port = 0)
public class ApplicationTest {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void contextLoads() throws Exception {
		// Stubs
	}


	@Test
	public void testCheckBalanceSuccess() {
		stubFor(get(urlEqualTo("/bank/balance/10001")).willReturn(
				aResponse().withBody("{\"Balance\":\"50000\"}").withHeader("Content-Type", "application/json")));
		webClient.get().uri("/bank/balance/10001").exchange().expectStatus().isOk().expectBody().jsonPath("$.Balance")
				.isEqualTo("50000");
	}

	@Test
	public void depositAmountSuccess() {
		stubFor(get(urlEqualTo("/bank/10001/35000")).willReturn(
				aResponse().withBody("{\"Balance\":\"60000\"}").withHeader("Content-Type", "application/json")));
		webClient.get().uri("/bank/10001/35000").exchange().expectStatus().isOk().expectBody().jsonPath("$.Balance")
				.isEqualTo("60000");
	}

	@Test
	void userByIdSuccess() {
		stubFor(get(urlEqualTo("/users/101")).willReturn(
				aResponse().withBody("{\"name\":\"Shantanu\"}").withHeader("Content-Type", "application/json")));
		webClient.get().uri("/users/101").exchange().expectStatus().isOk().expectBody().jsonPath("$.name")
				.isEqualTo("Shantanu");
	}
}

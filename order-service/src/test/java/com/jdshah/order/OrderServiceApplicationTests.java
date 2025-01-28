package com.jdshah.order;

import com.jdshah.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.shaded.org.hamcrest.MatcherAssert;
import org.testcontainers.shaded.org.hamcrest.Matchers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mysql = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mysql.start();
	}

	@Test
	void shouldPlaceOrder() {
		String requestBody = """
				{
				     "skuCode": "iphone_15",
				     "price": 1000,
				     "quantity": 100
				 }
			""";
		// creates stub api at wiremock server
		InventoryClientStub.stubInventoryCall("iphone_15", 100);
		String response = RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.post("/api/order")
				.then()
				.statusCode(201)
				.extract()
				.body()
				.asString();
		MatcherAssert.assertThat(response, Matchers.is("Order Placed Successfully"));
	}

}

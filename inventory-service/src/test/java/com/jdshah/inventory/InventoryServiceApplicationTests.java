package com.jdshah.inventory;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

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
	void shouldGiveInStockTrue() {
		Map<String, String> params = new HashMap<>();
		params.put("skuCode", "iphone_15");
		params.put("quantity", "100");
		boolean response = RestAssured.given()
				.contentType("application/json")
				.queryParams(params)
				.get("/api/inventory")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(Boolean.class);
		Assertions.assertTrue(response, "Product with given skuCode and quantity is not in Stock");
	}

	@Test
	void shouldGiveInStockFalse() {
		Map<String, String> params = new HashMap<>();
		params.put("skuCode", "iphone_15");
		params.put("quantity", "101");
		boolean response = RestAssured.given()
				.contentType("application/json")
				.queryParams(params)
				.get("/api/inventory")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(Boolean.class);
		Assertions.assertFalse(response, "Product with given skuCode and quantity is not in Stock");
	}

}

package com.jdshah.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/products/**")
						.uri("lb://PRODUCT-SERVICE"))
				.route(p -> p
						.path("/orders/**")
						.uri("lb://ORDER-SERVICE"))
				.route(p -> p
						.path("/inventory/**")
						.uri("lb://INVENTORY-SERVICE"))
				.build();
	}
}

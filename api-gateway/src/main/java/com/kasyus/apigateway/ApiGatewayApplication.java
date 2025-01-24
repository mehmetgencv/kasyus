package com.kasyus.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import java.time.LocalDateTime;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
			title = "Kasyus API Gateway",
			version = "1.0",
			description = "Gateway for Kasyus Microservices"
	)
)
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator kasyusRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				// Order Service Routes
				.route("order-service", p -> p
						.path("/order-service/api/v1/orders/**")
						.filters(f -> f
								.stripPrefix(1)
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://order-service"))

				// Product Service Routes
				.route("product-service", p -> p
						.path("/product-service/api/v1/products/**")
						.filters(f -> f
								.stripPrefix(1)
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://product-service"))

				// Swagger UI route for Product Service
				.route("product-service-swagger", p -> p
						.path("/v3/api-docs/product-service")
						.filters(f -> f.rewritePath(
								"/v3/api-docs/product-service",
								"/v3/api-docs"))
						.uri("lb://product-service"))

				// Swagger UI route for Order Service
				.route("order-service-swagger", p -> p
						.path("/v3/api-docs/order-service")
						.filters(f -> f.rewritePath(
								"/v3/api-docs/order-service",
								"/v3/api-docs"))
						.uri("lb://order-service"))

				// Swagger UI
				.route("swagger-ui", p -> p
						.path("/swagger-ui/**")
						.uri("lb://api-gateway"))

				.build();
	}
}

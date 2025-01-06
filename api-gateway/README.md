# API Gateway Service

Central entry point for the Kasyus E-Commerce Platform that handles routing and cross-cutting concerns.

## Features

- Dynamic routing to microservices
- Request rate limiting
- Circuit breaking
- Request/response logging
- Correlation ID propagation
- Error handling

## API Routes

### Product Service Routes
- All requests to `/api/v1/products/**` are routed to the Product Service

### Order Service Routes
- All requests to `/api/v1/orders/**` are routed to the Order Service

## Running Locally

1. Ensure the Discovery Service is running
2. Start the application:
```bash
mvn spring-boot:run
```

## Docker Support

Build the image:
```bash
docker build -t kasyus/api-gateway .
```

Run the container:
```bash
docker run -p 8080:8080 kasyus/api-gateway
```

## Monitoring

The service exposes the following actuator endpoints:
- Health: `/actuator/health`
- Info: `/actuator/info`
- Metrics: `/actuator/metrics`
- Routes: `/actuator/gateway/routes` 
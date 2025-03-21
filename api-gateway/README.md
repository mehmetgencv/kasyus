# 🛡️ Kasyus API Gateway

The central entry point for the **Kasyus E-Commerce Platform**, responsible for routing, authentication, authorization, rate limiting, and other cross-cutting concerns across microservices.

---

## 🚀 Features

- ✅ **JWT-based Authentication** (via Auth Service)
- 🔁 **Dynamic Routing** with Spring Cloud Gateway
- ⚙️ **Role-based Authorization** (ADMIN, SELLER, etc.)
- 🌐 **CORS Configuration** (for frontend access)
- ⛔ **Circuit Breaker** Support (Resilience4j)
- 📊 **Swagger UI Routing** per service
- 📉 **Rate Limiting** using Redis
- 📦 **Load-balanced WebClient** for internal service calls

---

## 🗺️ Route Overview

| Path Prefix                          | Target Microservice   |
|--------------------------------------|------------------------|
| `/auth-service/**`                  | `auth-service`         |
| `/user-service/**`                  | `user-service`         |
| `/order-service/api/v1/orders/**`   | `order-service`        |
| `/product-service/api/v1/**`        | `product-service`      |
| `/cart-service/api/v1/**`           | `cart-service`         |
| `/v3/api-docs/**`, `/swagger-ui/**` | Swagger for each service |

---

## 🔐 Security & Token Validation

- Each request is inspected for a `Bearer` token in the `Authorization` header.
- The token is verified by calling `auth-service`'s `/api/v1/auth/validate` endpoint.
- Upon success, the gateway adds:
    - `X-User-Id`
    - `X-User-Email`
    - `X-User-Roles`
      headers before forwarding the request.
- Access to protected routes is limited by user roles (`ROLE_ADMIN`, `ROLE_SELLER`, etc.).

---

## 📦 Running Locally (Java 21)

### Prerequisites

- Java 21
- Maven
- Discovery Service (Eureka) running

### Start the API Gateway

```bash
mvn spring-boot:run
```

---

## 🐳 Docker Support

### Build Docker Image

```bash
docker build -t kasyus/api-gateway .
```

### Run the Container

```bash
docker run -p 8072:8072 kasyus/api-gateway
```

---

## 📊 Monitoring Endpoints

The following [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/) endpoints are enabled:

| Endpoint                        | Description                 |
|----------------------------------|-----------------------------|
| `/actuator/health`             | Application health check    |
| `/actuator/info`               | Build & environment info    |
| `/actuator/metrics`            | Performance metrics         |
| `/actuator/gateway/routes`     | Defined gateway routes      |

---
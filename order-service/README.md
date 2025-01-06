# Order Service

Order management service for the Kasyus E-Commerce Platform.

## Features

- Order creation and management
- Order status tracking
- User order history
- Order search functionality

## API Endpoints

### Create Order
```http
POST /api/v1/orders
Content-Type: application/json

{
  "userId": "123",
  "orderNumber": "ORD-2024-001",
  "totalAmount": 99.99,
  "status": "PENDING"
}
```

### Get Order
```http
GET /api/v1/orders/{id}
GET /api/v1/orders/number/{orderNumber}
GET /api/v1/orders/user/{userId}
GET /api/v1/orders/status/{status}
```

### Update Order
```http
PUT /api/v1/orders/{id}
Content-Type: application/json

{
  "status": "COMPLETED",
  "totalAmount": 99.99
}
```

### Delete Order
```http
DELETE /api/v1/orders/{id}
```

## Database Schema

```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## Configuration

### Application Properties

```yaml
server:
  port: 8082

spring:
  application:
    name: order-service
  datasource:
    url: jdbc:postgresql://localhost:5432/kasyus_orders
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

## Running Locally

1. Ensure PostgreSQL is running
2. Create database:
```sql
CREATE DATABASE kasyus_orders;
```

3. Start the application:
```bash
mvn spring-boot:run
```

## Docker Support

Build the image:
```bash
docker build -t kasyus/order-service .
```

Run the container:
```bash
docker run -p 8082:8082 kasyus/order-service
```

## Testing

Run tests:
```bash
mvn test
```

## Monitoring

The service exposes the following actuator endpoints:
- Health: `/actuator/health`
- Info: `/actuator/info`
- Metrics: `/actuator/metrics` 
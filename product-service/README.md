# Product Service

Product catalog and inventory management service for the Kasyus E-Commerce Platform.

## Features

- Product CRUD operations
- Product search and filtering
- Category management
- Stock management
- Price management
- SKU tracking

## API Endpoints

### Create Product
```http
POST /api/v1/products
Content-Type: application/json

{
  "name": "Sample Product",
  "description": "Product description",
  "price": 29.99,
  "stockQuantity": 100,
  "category": "Electronics",
  "sku": "PROD-001"
}
```

### Get Product
```http
GET /api/v1/products/{id}
GET /api/v1/products/sku/{sku}
GET /api/v1/products/category/{category}
```

### Search Products
```http
GET /api/v1/products/search?name=keyword
GET /api/v1/products/search?minPrice=10&maxPrice=100
```

### Update Product
```http
PUT /api/v1/products/{id}
Content-Type: application/json

{
  "name": "Updated Product",
  "price": 39.99,
  "stockQuantity": 150
}
```

### Update Stock
```http
PATCH /api/v1/products/{id}/stock?quantity=50
```

### Delete Product
```http
DELETE /api/v1/products/{id}
```

## Database Schema

```sql
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INTEGER NOT NULL,
    category VARCHAR(100),
    sku VARCHAR(50) UNIQUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## Configuration

### Application Properties

```yaml
server:
  port: 8081

spring:
  application:
    name: product-service
  datasource:
    url: jdbc:postgresql://localhost:5432/kasyus_products
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
CREATE DATABASE kasyus_products;
```

3. Start the application:
```bash
mvn spring-boot:run
```

## Docker Support

Build the image:
```bash
docker build -t kasyus/product-service .
```

Run the container:
```bash
docker run -p 8081:8081 kasyus/product-service
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

## Additional Features

- Low stock alerts
- Price history tracking
- Category-based search
- Bulk import/export support 
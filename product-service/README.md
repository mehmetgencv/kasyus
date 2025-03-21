# 🛍️ Kasyus Product Service

The **Product Service** is responsible for managing products and categories within the Kasyus E-Commerce Platform. It supports full CRUD operations, image management via MinIO, and emits events such as price updates.

---

## 🚀 Features

- 📦 Create, update, delete, and retrieve products
- 🔎 Filter by category, SKU, or name
- 🏷️ Price updates with Kafka event publishing (`PriceUpdatedEvent`)
- 🖼️ Upload, update, and delete product images (stored in **MinIO**)
- 🏷️ Set cover image for each product
- 📁 Category management (CRUD operations)
- 🔐 Role-based access control (via API Gateway)

---

## 📦 API Endpoints

### 🔧 Products

| Method | Endpoint                        | Description                             |
|--------|----------------------------------|-----------------------------------------|
| POST   | `/api/v1/products`              | Create a new product                    |
| GET    | `/api/v1/products/{id}`         | Get product by ID                       |
| GET    | `/api/v1/products/sku/{sku}`    | Get product by SKU                      |
| GET    | `/api/v1/products`              | Get all products                        |
| GET    | `/api/v1/products/category/{id}`| Get products by category ID             |
| PUT    | `/api/v1/products/{id}`         | Update product                          |
| PATCH  | `/api/v1/products/{id}/price`   | Update product price & publish event    |
| DELETE | `/api/v1/products/{id}`         | Delete product                          |

### 🖼️ Images

| Method | Endpoint                                               | Description                             |
|--------|---------------------------------------------------------|-----------------------------------------|
| POST   | `/api/v1/products/{id}/images`                         | Upload multiple images                  |
| PUT    | `/api/v1/products/{id}/images/{imageId}`              | Update a specific image                 |
| PATCH  | `/api/v1/products/{id}/images/{imageId}/cover`        | Set specific image as cover             |
| DELETE | `/api/v1/products/{id}/images/{imageId}`              | Delete a specific image                 |

### 🗂️ Categories

| Method | Endpoint                  | Description            |
|--------|----------------------------|------------------------|
| GET    | `/api/v1/categories`      | Get all categories     |
| GET    | `/api/v1/categories/{id}` | Get category by ID     |
| POST   | `/api/v1/categories`      | Create category        |
| PUT    | `/api/v1/categories/{id}` | Update category        |
| DELETE | `/api/v1/categories/{id}` | Delete category        |

---

## 🧪 Example Requests

### ➕ Create Product

```http
POST /api/v1/products
Content-Type: application/json

{
  "name": "Gaming Laptop",
  "description": "High-performance laptop",
  "price": 1999.99,
  "categoryId": 1,
  "productType": "ELECTRONICS",
  "sellerId": 42,
  "sku": "GAM-1234"
}
```

### 💸 Price Update

```http
PATCH /api/v1/products/100/price
Content-Type: application/json

{
  "price": 1799.99
}
```

➡️ Triggers a `PriceUpdatedEvent` sent via Kafka topic: `product-price-updated`.

---

## 🖼️ Image Upload

```http
POST /api/v1/products/100/images
Content-Type: multipart/form-data

images: [file1.jpg, file2.jpg]
coverImageIndex: 0
```

Stored securely in **MinIO** bucket.

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **MinIO** for image storage
- **Kafka** for asynchronous events
- **REST API**
- **Swagger** for documentation

---

## 📤 Events

The service publishes a `PriceUpdatedEvent` when a product's price is changed:

```json
{
  "productId": 100,
  "newPrice": 1799.99,
  "timestamp": "2025-03-21T14:22:00Z"
}
```

---

## ⚙️ Configuration Notes

- MinIO endpoint and bucket name are configured via `application.yml`:
```yaml
minio:
  url: http://localhost:9000
  bucket-name: kasyus-products
```

- Kafka topic used: `product-price-updated`

---

## 🧪 Running Locally

### Prerequisites

- Java 21
- Maven
- MinIO
- Kafka (optional but recommended)

```bash
mvn spring-boot:run
```

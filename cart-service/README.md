Here's a professional **README** file for your `cart-service`, written in English and aligned with your code structure and architecture:

---

# 🛒 Kasyus Cart Service

The **Cart Service** is responsible for managing shopping cart operations within the Kasyus E-Commerce platform. It supports adding, updating, removing items from the cart, clearing the cart, and syncing price changes from the Product Service.

---

## 🚀 Features

- 🧾 Add, update, and remove products in the cart
- 🧼 Clear entire cart
- 💰 Recalculate cart total on every update
- 🔄 Sync product price changes via event (`PriceUpdatedEvent`)
- 📦 Cart per user (based on `X-User-Id` header)
- ✅ Transactional consistency
- 🧭 Lightweight REST API with meaningful responses

---

## 📦 API Endpoints

| Method | Endpoint           | Description                          | Header         |
|--------|--------------------|--------------------------------------|----------------|
| GET    | `/api/v1/carts`    | Get cart by user                     | `X-User-Id`    |
| POST   | `/api/v1/carts/add` | Add product to cart                  | `X-User-Id`    |
| PUT    | `/api/v1/carts/update` | Update quantity of product in cart | `X-User-Id`    |
| DELETE | `/api/v1/carts/remove?productId={id}` | Remove product from cart | `X-User-Id` |
| DELETE | `/api/v1/carts/clear` | Clear entire cart for user         | `X-User-Id`    |

---

## 🧪 Example Usage

### ➕ Add Item to Cart

```http
POST /api/v1/carts/add
X-User-Id: 1234
Content-Type: application/json

{
  "productId": 101,
  "quantity": 2,
  "price": 49.99
}
```

### 🔁 Update Item Quantity

```http
PUT /api/v1/carts/update
X-User-Id: 1234
Content-Type: application/json

{
  "productId": 101,
  "quantity": 5
}
```

### ❌ Remove Item

```http
DELETE /api/v1/carts/remove?productId=101
X-User-Id: 1234
```

### 🧼 Clear Cart

```http
DELETE /api/v1/carts/clear
X-User-Id: 1234
```

---

## 📤 Event Handling

The service listens for `PriceUpdatedEvent` (e.g., via Kafka or other async messaging) to automatically update product prices inside user carts and recalculate total prices accordingly.

```json
{
  "productId": 101,
  "newPrice": 44.99,
  "timestamp": "2025-03-21T15:00:00"
}
```

---

## 🛠 Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Kafka (optional, for event handling)**
- **RESTful API Design**

---

## 🧪 Run Locally

### Requirements

- Java 21
- Maven
- Database PostgreSQL

```bash
mvn spring-boot:run
```


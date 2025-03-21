# 👤 Kasyus User Service

The **User Service** is responsible for managing user profiles, addresses, payment methods, and wishlist items in the **Kasyus E-Commerce Platform**. It also listens to user registration events from the Auth Service via Kafka and persists newly registered users.

---

## 🚀 Features

- 👥 User profile management (view/update)
- 🏠 Address CRUD operations
- 💳 Payment method management
- ❤️ Wishlist item handling
- 📩 Kafka event consumer for `USER_REGISTERED` events
- ☁️ Stateless with user context passed via `X-User-Id` header

---

## 🧾 API Endpoints

### 👤 Profile

| Method | Endpoint               | Description               |
|--------|------------------------|---------------------------|
| GET    | `/api/v1/users/me`     | Get current user's profile |
| PUT    | `/api/v1/users/me/profile` | Update user profile    |

### 🏠 Address Management

| Method | Endpoint                         | Description              |
|--------|----------------------------------|--------------------------|
| POST   | `/api/v1/users/me/addresses`     | Add new address          |
| GET    | `/api/v1/users/me/addresses`     | List all addresses       |
| PUT    | `/api/v1/users/me/addresses/{id}`| Update address           |
| DELETE | `/api/v1/users/me/addresses/{id}`| Delete address           |

### 💳 Payment Methods

| Method | Endpoint                                | Description                |
|--------|-----------------------------------------|----------------------------|
| POST   | `/api/v1/users/me/payment-methods`      | Add new payment method     |
| GET    | `/api/v1/users/me/payment-methods`      | List payment methods       |
| PUT    | `/api/v1/users/me/payment-methods/{id}` | Update payment method      |
| DELETE | `/api/v1/users/me/payment-methods/{id}` | Delete payment method      |

### ❤️ Wishlist

| Method | Endpoint                                      | Description              |
|--------|-----------------------------------------------|--------------------------|
| POST   | `/api/v1/users/me/wishlist-items`            | Add item to wishlist     |
| GET    | `/api/v1/users/me/wishlist-items`            | Get all wishlist items   |
| DELETE | `/api/v1/users/me/wishlist-items/{itemId}`   | Remove item from wishlist|

---

## 🧪 Example Request – Update Profile

```http
PUT /api/v1/users/me/profile
X-User-Id: 123456
Content-Type: application/json

{
  "firstName": "Alice",
  "lastName": "Doe",
  "email": "alice@example.com",
  "phoneNumber": "+1234567890",
  "dateOfBirth": "1995-04-15",
  "customerSegment": "Premium",
  "loyaltyPoints": 150
}
```

---

## 📩 Kafka Integration

The service listens to the `auth-events` topic for `USER_REGISTERED` events and persists the user data if not already present.

**Sample Event:**
```json
{
  "eventType": "USER_REGISTERED",
  "userId": "123456",
  "email": "alice@example.com",
  "firstName": "Alice",
  "lastName": "Doe",
  "role": "CUSTOMER",
  "createdBy": "alice@example.com",
  "timestamp": "2025-03-21T14:00:00"
}
```

---

## 🛠 Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Kafka** (Event-Driven Communication)
- **REST API**
- **Swagger/OpenAPI** (optional)

---

## 📦 Headers Used

| Header        | Purpose                     |
|---------------|-----------------------------|
| `X-User-Id`   | Identifies the current user |

---

## 🧪 Run Locally

### Requirements

- Java 21
- Maven
- Kafka (for event consumption)
- PostgreSQL or H2

```bash
mvn spring-boot:run
```

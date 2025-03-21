
# 🔐 Kasyus Auth Service

The **Authentication & Authorization Service** for the Kasyus E-Commerce platform.  
Handles user registration, login, token generation, validation, logout, and user-related events.

---

## 🚀 Features

- ✅ User registration & login
- 🔐 JWT-based Access & Refresh Token management
- 🔄 Token refresh endpoint
- 🧪 Token validation for API Gateway
- 🚪 Logout support with token blacklisting
- 📤 Event publishing via Outbox pattern (e.g., `USER_REGISTERED`, `USER_LOGGED_IN`)
- 📂 Role-based access (ADMIN, SELLER, CUSTOMER)

---

## 📦 API Endpoints

| Method | Endpoint               | Description                         |
|--------|------------------------|-------------------------------------|
| POST   | `/api/v1/auth/register` | Register a new user                 |
| POST   | `/api/v1/auth/login`    | Login with email and password       |
| POST   | `/api/v1/auth/refresh`  | Refresh tokens using refresh token |
| POST   | `/api/v1/auth/validate` | Validate token (for API Gateway)    |
| POST   | `/api/v1/auth/logout`   | Invalidate access and refresh tokens |

---

## 🧪 Request/Response Samples

### ✅ Register

```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "StrongPass123!",
  "role": "SELLER"
}
```

### 🔐 Login

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "StrongPass123!"
}
```

**Response:**
```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6...",
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6...",
  "token_type": "Bearer"
}
```

### 🔄 Refresh Token

```http
POST /api/v1/auth/refresh
Authorization: Bearer <refresh_token>
```

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **JWT**
- **Spring Validation**
- **Outbox Pattern** for publishing domain events
- **Token Blacklisting** for logout support

---

## 📤 Auth Events

| Event Name        | Triggered On        |
|-------------------|---------------------|
| `USER_REGISTERED` | On user registration |
| `USER_LOGGED_IN`  | On successful login  |
| `USER_LOGGED_OUT` | On logout            |

These events are sent using the Outbox Pattern and can be consumed by other services (e.g., User Service, Notification Service).

---

## 🧪 Running Locally

### Prerequisites

- Java 21
- Maven
- PostgreSQL (or your preferred DB)
- Kafka (optional for event publishing if consuming outbox)

### Run

```bash
mvn spring-boot:run
```


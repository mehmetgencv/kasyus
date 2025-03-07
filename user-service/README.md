# User Service

Extended user management service that complements Keycloak for e-commerce specific requirements.

## Why User Service with Keycloak?

While Keycloak handles:
- Authentication
- Basic user information
- Role-based authorization
- SSO and OAuth2/OIDC

User Service extends functionality with:
1. **E-commerce Specific User Data**:
   - Shipping addresses
   - Billing information
   - Shopping preferences
   - Wishlist management
   - Order history

2. **Advanced User Profiles**:
   - Customer segments
   - Shopping behavior analytics
   - Personalization preferences
   - Customer loyalty programs
   - User activity tracking

3. **Business Logic**:
   - Custom validation rules
   - User verification workflows
   - Fraud detection integration
   - Customer scoring
   - Recommendation data

## Architecture

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Client    │────▶│ API Gateway │────▶│  Keycloak   │
└─────────────┘     └──────┬──────┘     └──────┬──────┘
                           │                    │
                    ┌──────▼──────┐      ┌─────▼─────┐
                    │    User     │◀─────│  Keycloak │
                    │   Service   │      │  Webhook  │
                    └──────┬──────┘      └───────────┘
                           │
                    ┌──────▼──────┐
                    │  User Data  │
                    │  Database   │
                    └─────────────┘
```

## Database Schema

```sql
CREATE TABLE users (
    id UUID PRIMARY KEY,
    keycloak_id VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE user_profiles (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    phone_number VARCHAR(20),
    date_of_birth DATE,
    preferred_language VARCHAR(10),
    marketing_preferences JSONB,
    customer_segment VARCHAR(50),
    loyalty_points INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE addresses (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    type VARCHAR(20), -- SHIPPING, BILLING
    is_default BOOLEAN DEFAULT false,
    street_address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE payment_methods (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    type VARCHAR(50),
    is_default BOOLEAN DEFAULT false,
    provider VARCHAR(50),
    token VARCHAR(255),
    last_four VARCHAR(4),
    expiry_date DATE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE wishlists (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    product_id UUID,
    added_at TIMESTAMP NOT NULL
);
```

## API Endpoints

### User Profile Management
```
GET    /api/v1/users/{id}/profile
POST   /api/v1/users/{id}/profile
PUT    /api/v1/users/{id}/profile
DELETE /api/v1/users/{id}/profile
```

### Address Management
```
GET    /api/v1/users/{id}/addresses
POST   /api/v1/users/{id}/addresses
PUT    /api/v1/users/{id}/addresses/{addressId}
DELETE /api/v1/users/{id}/addresses/{addressId}
PATCH  /api/v1/users/{id}/addresses/{addressId}/make-default
```

### Payment Methods
```
GET    /api/v1/users/{id}/payment-methods
POST   /api/v1/users/{id}/payment-methods
DELETE /api/v1/users/{id}/payment-methods/{methodId}
PATCH  /api/v1/users/{id}/payment-methods/{methodId}/make-default
```

### Wishlist Management
```
GET    /api/v1/users/{id}/wishlist
POST   /api/v1/users/{id}/wishlist
DELETE /api/v1/users/{id}/wishlist/{productId}
```

## Keycloak Integration

### User Creation Flow
1. User registers through Keycloak
2. Keycloak webhook triggers User Service
3. User Service creates extended profile
4. Return success/failure to Keycloak

```java
@PostMapping("/webhook/user-created")
public ResponseEntity<Void> handleUserCreated(@RequestBody KeycloakUserEvent event) {
    userService.createUserProfile(
        UserProfile.builder()
            .keycloakId(event.getUserId())
            .email(event.getEmail())
            .build()
    );
    return ResponseEntity.ok().build();
}
```

### Authentication Flow
1. Client authenticates with Keycloak
2. Receives JWT token
3. Uses token for User Service requests
4. User Service validates token with Keycloak

```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthenticationConverter());
        return http.build();
    }
}
```

## Event Integration

### Keycloak Events
- USER_CREATED
- USER_UPDATED
- USER_DELETED
- ROLE_UPDATED

### User Service Events
- PROFILE_UPDATED
- ADDRESS_ADDED
- PAYMENT_METHOD_ADDED
- WISHLIST_UPDATED

## Deployment

### Environment Variables
```yaml
USER_SERVICE_DB_URL: jdbc:postgresql://localhost:5432/users
USER_SERVICE_DB_USERNAME: user_service
USER_SERVICE_DB_PASSWORD: password
KEYCLOAK_AUTH_SERVER_URL: http://keycloak:8080/auth
KEYCLOAK_REALM: ecommerce
KEYCLOAK_CLIENT_ID: user-service
KEYCLOAK_CLIENT_SECRET: secret
```

### Kubernetes Configuration
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-service
spec:
  replicas: 2
  template:
    spec:
      containers:
        - name: user-service
          image: user-service:latest
          ports:
            - containerPort: 8080
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: prod
            - name: KEYCLOAK_AUTH_SERVER_URL
              valueFrom:
                configMapKeyRef:
                  name: keycloak-config
                  key: auth-server-url
```

## Monitoring

### Health Checks
```
/actuator/health
/actuator/info
/actuator/metrics
```

### Key Metrics
- User profile completion rate
- Address verification success rate
- Payment method addition success rate
- API response times
- Error rates by endpoint

## Security Considerations

1. **Data Protection**:
   - Encryption at rest
   - Secure communication
   - PCI compliance for payment data

2. **Access Control**:
   - Role-based access
   - Resource-level permissions
   - API rate limiting

3. **Audit Logging**:
   - User activity tracking
   - Change history
   - Security events 
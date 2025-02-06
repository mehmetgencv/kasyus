# Kasyus API Usage Guide

This guide explains how to set up authentication and make API requests to the Kasyus platform.

## 1. Keycloak Initial Setup

### Access Keycloak Admin Console
1. Go to http://localhost:7080/
2. Login with:
   ```
   Username: admin
   Password: admin
   ```

### Create Client for Service Account
1. Navigate to "Clients" in the left sidebar
2. Click "Create client"
3. Fill in the details:
   ```
   Client ID: kasyus-callcenter-cc
   ```
4. Click "Next"
5. Configure authentication:
   ```
   Client authentication: ON
   Authentication flow: Check only Service accounts roles
   ```
6. Click "Next"
7. Click "Save"

### Create Roles
1. Go to "Realm Roles"
2. Click "Create role"
3. Create the following roles:
   ```
   Role name: Orders
   Description: Access to order management APIs
   ```
   Click "Save"

   ```
   Role name: Products
   Description: Access to product management APIs
   ```
   Click "Save"

### Assign Roles to Service Account
1. Go back to "Clients"
2. Select "kasyus-callcenter-cc"
3. Navigate to "Service Account Roles" tab
4. Click "Assign Role"
5. Filter by realm roles
6. Select both "Orders" and "Products" roles
7. Click "Assign"

### Get Client Credentials
1. Still in the client configuration
2. Go to "Credentials" tab
3. Copy the "Client Secret"
   - Keep this secret secure, you'll need it for API requests

## 2. Making API Requests with Postman

### Configure OAuth 2.0 Token
1. Open Postman
2. Create a new request
3. Go to "Authorization" tab
4. Select "OAuth 2.0"
5. Click "Configure New Token"
6. Fill in the token configuration:
   ```
   Token Name: clientcredentials_accesstoken
   Grant Type: Client Credentials
   Access Token URL: http://localhost:7080/realms/master/protocol/openid-connect/token
   Client ID: kasyus-callcenter-cc
   Client Secret: [Your copied client secret]
   Scope: openid email profile
   Client Authentication: Send client credentials in body
   ```
7. Click "Get New Access Token"
8. Click "Use Token"

### Create Regular User
1. Navigate to "Users" in the left sidebar
2. Click "Add user"
3. Fill in the user details:
   ```
   Username: john.doe
   Email: john.doe@example.com
   First Name: John
   Last Name: Doe
   ```
4. Click "Create"
5. Go to "Credentials" tab
6. Set password:
   ```
   Password: your_secure_password
   Temporary: OFF
   ```
7. Click "Set Password"
8. Go to "Role Mappings" tab
9. Under "Realm Roles", assign appropriate roles (e.g., "user", "customer")

### User Authentication
Users can authenticate using:
1. Direct Login:
   ```
   POST http://localhost:7080/realms/master/protocol/openid-connect/token
   Content-Type: application/x-www-form-urlencoded

   grant_type: password
   client_id: kasyus-client
   username: john.doe
   password: your_secure_password
   ```

2. Web Login:
   - Redirect users to: `http://localhost:7080/realms/master/protocol/openid-connect/auth`
   - After successful login, they will be redirected back with an authorization code
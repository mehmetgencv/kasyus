<div align="center">
  <img src="public/kasyus.svg" alt="Kasyus Logo" width="400">
</div>

# Kasyus E-Commerce Platform

A modern, scalable e-commerce platform built with microservices architecture using Spring Cloud and Docker.

## Overview

This project implements a microservices-based e-commerce system with the following services:

- **API Gateway (Port: 8072)**: Central entry point for client requests, handling routing and cross-cutting concerns
- **Discovery Service (Port: 8761)**: Service registry and discovery using Netflix Eureka
- **Order Service (Port: 8082)**: Order processing and management
- **Product Service (Port: 8081)**: Product catalog and inventory management
- **Message Service (Port: 9010)**: Handles messaging and notifications

## Architecture

```
┌─────────────┐     ┌─────────────┐
│   Client    │────▶│ API Gateway │
└─────────────┘     └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │  Discovery  │
                    │   Service   │
                    └──────┬──────┘
                           │
         ┌─────────────────┴─────────────────┐
         │                │                 │
   ┌─────▼─────┐   ┌──────▼─────┐    ┌─────▼─────┐
   │   Order   │   │  Product   │    │  Message  │
   │  Service  │   │  Service   │    │  Service  │
   └───────────┘   └────────────┘    └───────────┘
```

## Technical Stack

### Core Technologies
- Java 21
- Spring Boot 3.4.1
- Spring Cloud
- Spring Security with Keycloak
- PostgreSQL
- Redis
- Apache Kafka
- RabbitMQ
- Docker & Docker Compose

### Infrastructure
- Service Discovery: Netflix Eureka
- API Gateway: Spring Cloud Gateway
- Authentication: Keycloak (Port: 7080)
- Cache: Redis (Port: 6379)
- Message Broker: RabbitMQ (Ports: 5672, 15672)
- Database: PostgreSQL (Port: 5432)

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 21 JDK (for local development)
- Maven (for local development)

### Quick Start

1. Clone the repository:
```bash
git clone https://github.com/mehmetgencv/kasyus.git
cd kasyus
```

2. Start all services using Docker Compose:
```bash
cd docker-compose/default
docker-compose up -d
```

### Deployment Options

### Kubernetes Deployment

For production-grade deployment using Kubernetes, refer to our [Kubernetes Deployment Guide](kubernetes/README.md).

Key features of our Kubernetes deployment:
- Scalable microservices architecture
- Automated health checks and recovery
- Resource management and monitoring
- Persistent storage for databases and caches
- Service discovery and load balancing

To get started with Kubernetes deployment:
```bash
cd kubernetes
kubectl apply -f 2_configmaps.yaml
# Follow the deployment guide for complete instructions
```

### Service URLs

- API Gateway: http://localhost:8072
- Discovery Service Dashboard: http://localhost:8761
- Keycloak Admin Console: http://localhost:7080
- RabbitMQ Management UI: http://localhost:15672

## Authentication and API Usage

For detailed instructions on setting up authentication and using the APIs, please refer to our [Usage Guide](public/kasyus-usage.md). The guide covers:

- Setting up Keycloak authentication
- Creating service accounts and users
- Assigning roles and permissions
- Making authenticated API requests
- Error handling and troubleshooting

### Authentication Setup

1. Access Keycloak Admin Console:
   - URL: http://localhost:7080
   - Username: admin
   - Password: admin

2. Create a New Realm:
   - Click "Create Realm"
   - Name it `kasyus`
   - Click "Create"

3. Create a Client:
   - Go to "Clients" → "Create client"
   - Client ID: `kasyus-client`
   - Client Protocol: `openid-connect`
   - Click "Next"
   - Valid redirect URIs: `http://localhost:8072/*`
   - Web Origins: `http://localhost:8072`
   - Click "Save"

4. Create a User:
   - Go to "Users" → "Add user"
   - Username: Enter desired username
   - Email: Enter email
   - Click "Create"
   - Go to "Credentials" tab
   - Set password and disable "Temporary" option
   - Click "Set Password"

5. Assign Roles:
   - Go to "Roles" → "Create role"
   - Create roles: `ROLE_USER`, `ROLE_ADMIN`
   - Go to "Users" → Select your user
   - Go to "Role Mapping"
   - Assign desired roles

### Making Authenticated Requests

To make requests to the API, you need to:

1. Get an access token:
```bash
curl -X POST http://localhost:7080/realms/kasyus/protocol/openid-connect/token \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'client_id=kasyus-client' \
  -d 'grant_type=password' \
  -d 'username=YOUR_USERNAME' \
  -d 'password=YOUR_PASSWORD'
```

2. Use the token in your requests:
```bash
curl -X GET http://localhost:8072/api/v1/products \
  -H 'Authorization: Bearer YOUR_ACCESS_TOKEN'
```

### Database Configuration

The PostgreSQL instance is configured with:
- Username: kasyus
- Password: kasyus123
- Databases: kasyus_products, kasyus_orders

### Monitoring

Each service exposes the following actuator endpoints:
- Health: `/actuator/health`
- Info: `/actuator/info`
- Metrics: `/actuator/metrics`

## Service Documentation

For detailed setup and configuration of each service, please refer to their respective README files:

- [API Gateway](api-gateway/README.md)
- [Discovery Service](discovery-service/README.md)
- [Order Service](order-service/README.md)
- [Product Service](product-service/README.md)
- [Message Service](message/README.md)

## Development

### Building Services Locally

Each service can be built using Maven:

```bash
mvn clean install
```

### Running Services Locally

To run a service locally:

```bash
mvn spring-boot:run
```

Note: When running services locally, ensure the Discovery Service is running first.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
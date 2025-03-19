<div align="center">
  <img src="public/kasyus.svg" alt="Kasyus Logo" width="400">
</div>

# Kasyus E-Commerce Platform

A modern, scalable e-commerce platform built with microservices architecture using Spring Cloud and Docker.

## What is Kasyus?
Kasyus is named after **Kasyus (Kassius) Mountain**, also known as **Kel Dağı**, a dormant volcanic mountain in Hatay, Turkey. The mountain has historical significance and is known for its rich biodiversity and ancient ruins, including the St. Barlaam Monastery from the Roman era.

## Overview

This project implements a microservices-based e-commerce system with the following services:

- **API Gateway (Port: 8072)**: Central entry point for client requests, handling routing and cross-cutting concerns
- **Discovery Service (Port: 8761)**: Service registry and discovery using Netflix Eureka
- **Auth Service (Port: 8088)**: Handles authentication and user management
- **User Service (Port: 8082)**: Manages user profiles and accounts
- **Order Service (Port: 8083)**: Order processing and management
- **Product Service (Port: 8081)**: Product catalog and inventory management
- **Message Service (Port: 9010)**: Handles messaging and notifications
- **MinIO (Port: 9001)**: Object storage service for handling media assets
- **Kafka**: Distributed event streaming platform for real-time messaging

## Architecture


<p align="center">
  <img src="public/diagram/KasyusArchitectureDiagram.png" width="400">
</p>


## Technical Stack

### Core Technologies
- Java 21
- Spring Boot 3.4.1
- Spring Cloud
- Spring Security (Custom Auth Service)
- PostgreSQL
- Redis
- Apache Kafka
- MinIO (Object Storage)
- Docker & Docker Compose

### Infrastructure
- Service Discovery: Netflix Eureka
- API Gateway: Spring Cloud Gateway
- Authentication: Custom Auth Service
- Cache: Redis (Port: 6379)
- Database: PostgreSQL (Port: 5432)
- Storage: MinIO (Port: 9001)

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

## Frontend
Kasyus also includes a frontend built with React. You can find it here: [Kasyus Frontend](https://github.com/mehmetgencv/kasyus-fe)

To run the frontend locally:
```bash
git clone https://github.com/mehmetgencv/kasyus-fe.git
cd kasyus-fe
npm install
npm start
```

## Deployment Options

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
kubectl apply -f 1_configmaps.yaml
# Follow the deployment guide for complete instructions
```

### Service URLs

- API Gateway: http://localhost:8072
- Discovery Service Dashboard: http://localhost:8761
- MinIO Console: http://localhost:9001

## Authentication and API Usage

For detailed instructions on setting up authentication and using the APIs, please refer to our [Usage Guide](public/kasyus-usage.md). The guide covers:

- Creating service accounts and users
- Assigning roles and permissions
- Making authenticated API requests
- Error handling and troubleshooting

### Authentication Setup

Users can register via the Auth Service:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "securepassword",
  "role": "USER"
}
```

2. Use the token in your requests:
```bash
curl -X GET http://localhost:8072/api/v1/products \
  -H 'Authorization: Bearer YOUR_ACCESS_TOKEN'
```

## Database Configuration

The PostgreSQL instance is configured with:
- Username: kasyus
- Password: kasyus123
- Databases: kasyus_products, kasyus_orders

## Monitoring

Each service exposes the following actuator endpoints:
- Health: `/actuator/health`
- Info: `/actuator/info`
- Metrics: `/actuator/metrics`

## Service Documentation

For detailed setup and configuration of each service, please refer to their respective README files:

- [API Gateway](api-gateway/README.md)
- [Discovery Service](discovery-service/README.md)
- [Auth Service](auth-service/README.md)
- [User Service](user-service/README.md)
- [Order Service](order-service/README.md)
- [Product Service](product-service/README.md)
- [Message Service](message/README.md)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


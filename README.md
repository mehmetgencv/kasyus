# Kasyus E-Commerce Platform

A modern, scalable e-commerce platform built with microservices architecture.

## Overview

This project implements a microservices-based e-commerce system with the following core services:

- **API Gateway**: Central entry point for client requests
- **Discovery Service**: Service registry and discovery
- **Order Service**: Order processing and management
- **Product Service**: Product catalog and inventory management

Each service is independently deployable and has its own README with detailed documentation.

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
         │                                   │
   ┌─────▼─────┐                     ┌──────▼─────┐
   │   Order   │                     │  Product   │
   │  Service  │                     │  Service   │
   └───────────┘                     └────────────┘
```

## Technical Stack

- Java 21
- Spring Boot 3.4.1
- Spring Cloud
- PostgreSQL
- Docker
- Maven

## Getting Started

### Prerequisites

- Docker & Docker Compose

### Quick Start

1. Clone the repository:
```bash
git clone https://github.com/mehmetgencv/kasyus.git
cd kasyus
```

2. Start all services:
```bash
docker-compose up -d
```

For detailed setup and configuration of each service, please refer to their respective README files:

- [API Gateway](api-gateway/README.md)
- [Discovery Service](discovery-service/README.md)
- [Order Service](order-service/README.md)
- [Product Service](product-service/README.md)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
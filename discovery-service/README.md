# Discovery Service

Service registry and discovery server for the Kasyus E-Commerce Platform using Netflix Eureka.

## Features

- Service registration
- Service discovery
- Health monitoring
- Dashboard UI for service status

## Configuration

### Application Properties

```yaml
server:
  port: 8761

spring:
  application:
    name: discovery-service

eureka:
  client:
    registerWithEureka: false
    fetchRegistry: false
  server:
    waitTimeInMsWhenSyncEmpty: 0
    enableSelfPreservation: false
```

## Dashboard

The Eureka dashboard is available at `http://localhost:8761` and shows:
- Registered instances
- System status
- Instance health
- Uptime metrics

## Running Locally

Start the application:
```bash
mvn spring-boot:run
```

## Docker Support

Build the image:
```bash
docker build -t kasyus/discovery-service .
```

Run the container:
```bash
docker run -p 8761:8761 kasyus/discovery-service
```

## Monitoring

The service exposes the following actuator endpoints:
- Health: `/actuator/health`
- Info: `/actuator/info`
- Metrics: `/actuator/metrics`

## Important Notes

- This service should be started first before other services
- In production, consider running multiple instances for high availability
- Configure appropriate security settings in production 
# Kubernetes Deployment Guide

This guide explains how to deploy and manage Kasyus services in Kubernetes. For general project information and setup, please refer to the [main README](../README.md).

## Directory Structure
```
kubernetes/
├── 1_configmaps.yaml       # Common configuration          
├── 2_eurekaserver.yml      # Service discovery
├── 3_gateway.yml          # API Gateway
├── 4_auth.yml           #  authentication service
├── 5_users.yml           # Users service
├── 6_products.yml          # Product service
├── 7_carts.yml          # Cart service
├── 8_orders.yml          # Order service
├── 9_message.yml          # Message service
├── 31_redis.yml            # Redis cache
├── 32_minio.yml          # Object storage
├── 33_postgres.yml        # Database
├── 34_alloy.yml          # Monitoring
└── 35_loki.yml            # Logging infrastructure

```

## Prerequisites
- Kubernetes cluster (local or cloud)
- kubectl CLI tool
- Docker Desktop (for local development)

## Dashboard Access

1. Access Kubernetes Dashboard UI:
```bash
kubectl -n kubernetes-dashboard port-forward svc/kubernetes-dashboard-kong-proxy 8443:443
```

2. Create Admin Token:
```bash
kubectl -n kubernetes-dashboard create token admin-user
```

3. Open Dashboard:
   - Visit: https://localhost:8443
   - Use the token created in step 2

## Deploying Services

### Deploy All Services
Deploy all services in the correct order:

```bash
# 1. Create ConfigMaps and Secrets first
kubectl apply -f 1_configmaps.yaml

# 2. Deploy Infrastructure Services
kubectl apply -f 1_keycloak.yml
kubectl apply -f 2_eurekaserver.yml
kubectl apply -f 33_postgres.yml
kubectl apply -f 31_redis.yml
kubectl apply -f 32_minio.yml

# 3. Deploy Monitoring Services
kubectl apply -f 35_loki.yml
kubectl apply -f 34_alloy.yml

# 4. Deploy Application Services
kubectl apply -f 6_products.yml
kubectl apply -f 8_orders.yml
kubectl apply -f 3_gateway.yml
kubectl apply -f 9_message.yml
```

### Deploy Individual Service
To deploy a specific service:
```bash
kubectl apply -f <service-name>.yml
```

## Monitoring Deployments

### Check Service Status
```bash
# Check all resources
kubectl get all

# Check specific resource types
kubectl get pods
kubectl get services
kubectl get deployments
kubectl get statefulset
```

### View Logs
```bash
# View service logs
kubectl logs -f deployment/product-service
kubectl logs -f deployment/order-service
kubectl logs -f deployment/api-gateway

# View infrastructure logs
kubectl logs -f statefulset/postgres
kubectl logs -f deployment/redis
```

### Check Service Health
```bash
# Describe specific pod
kubectl describe pod <pod-name>

# Check endpoints
kubectl get endpoints
```

## Scaling Services

### Scale Deployments
```bash
# Scale a deployment
kubectl scale deployment/product-service --replicas=3
kubectl scale deployment/order-service --replicas=2
```

## Cleanup

### Remove All Resources
```bash
# Delete all resources in reverse order
kubectl delete -f 9_message.yml
kubectl delete -f 3_gateway.yml
kubectl delete -f 8_orders.yml
kubectl delete -f 6_products.yml
kubectl delete -f 34_alloy.yml
kubectl delete -f 35_loki.yml
kubectl delete -f 32_minio.yml
kubectl delete -f 31_redis.yml
kubectl delete -f 33_postgres.yml
kubectl delete -f 2_eurekaserver.yml
kubectl delete -f 1_keycloak.yml
kubectl delete -f 1_configmaps.yaml
```

### Remove Single Service
```bash
kubectl delete -f <service-name>.yml
```

## Troubleshooting

### Common Issues

1. **Pods Not Starting**
   ```bash
   kubectl describe pod <pod-name>
   kubectl logs <pod-name>
   ```

2. **Service Discovery Issues**
   ```bash
   kubectl get endpoints
   kubectl describe service <service-name>
   ```

3. **Storage Issues**
   ```bash
   kubectl get pv
   kubectl get pvc
   ```

### Health Checks
```bash
# Check readiness and liveness
kubectl describe pod <pod-name> | grep -A 10 "Conditions"
```

## Port Forwarding

For local development and testing:

```bash
# API Gateway
kubectl port-forward service/api-gateway 8072:8072

# Keycloak
kubectl port-forward service/keycloak 7080:8080

# PostgreSQL
kubectl port-forward service/postgres 5432:5432
```

## Resource Management

### View Resource Usage
```bash
kubectl top pods
kubectl top nodes
```

### Update Resource Limits
```bash
kubectl set resources deployment/product-service \
  --requests=cpu=200m,memory=256Mi \
  --limits=cpu=500m,memory=512Mi
```

For more detailed information about each service, refer to their respective YAML files in this directory. 
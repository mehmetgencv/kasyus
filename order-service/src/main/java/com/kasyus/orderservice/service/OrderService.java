package com.kasyus.orderservice.service;

import com.kasyus.orderservice.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order);
    Optional<Order> getOrderById(Long id);
    Optional<Order> getOrderByOrderNumber(String orderNumber);
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getOrdersByStatus(String status);
    List<Order> getAllOrders();
    Order updateOrder(Order order);
    void deleteOrder(Long id);
} 
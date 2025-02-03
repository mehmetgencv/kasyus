package com.kasyus.orderservice.service;

import com.kasyus.orderservice.dto.OrderMsgDto;
import com.kasyus.orderservice.model.Order;
import com.kasyus.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final StreamBridge streamBridge;

    public OrderServiceImpl(OrderRepository orderRepository, StreamBridge streamBridge) {
        this.orderRepository = orderRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        sendCommunication(savedOrder, savedOrder);
        return savedOrder;
    }

    private void sendCommunication(Order order, Order savedOrder) {
        OrderMsgDto orderMsgDto = new OrderMsgDto(order.getId(), order.getUserId(), order.getStatus());
        log.info("Sending communication request for the details {}", orderMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", orderMsgDto);
        log.info("Is the Communication request triggered?: {}", result);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(Order order) {
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public boolean updateCommunicationStatus(Long orderId) {
        boolean isUpdated = false;
        if(orderId == null) {
            return isUpdated;
        }
        Order order = orderRepository.findById(orderId).orElseThrow(
                ()-> new RuntimeException("Order not found")
        );
        order.setCommunicationSw(true);
        orderRepository.save(order);
        isUpdated = true;
        return true;
    }
} 
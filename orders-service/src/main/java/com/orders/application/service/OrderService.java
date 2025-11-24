package com.orders.application.service;

import com.orders.application.command.CreateOrderCommand;
import com.orders.domain.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderService {
    @Transactional
    UUID createOrder(CreateOrderCommand command) throws Exception;

    Order getOrderById(UUID orderId);

    Iterable<Order> getOrdersByUserId(UUID userId);
}

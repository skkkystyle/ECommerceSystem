package com.orders.application.service;

import com.orders.application.command.CreateOrderCommand;
import com.orders.domain.Order;
import com.orders.domain.OrderStatus;
import com.orders.infrastructure.outbox.OutboxService;
import com.orders.infrastructure.repository.OrderEntity;
import com.orders.infrastructure.repository.OrderJpaRepository;
import com.orders.application.event.OrderCreatedEvent;
import com.orders.application.event.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderJpaRepository orderRepository;
    private final OrderEventPublisher eventPublisher;
    private final OutboxService outboxService;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    @Override
    public UUID createOrder(CreateOrderCommand command) throws Exception {
        UUID orderId = UUID.randomUUID();

        Order order = Order.builder()
                .id(orderId)
                .userId(command.getUserId())
                .amount(command.getAmount())
                .description(command.getDescription())
                .status(OrderStatus.NEW)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        orderRepository.save(OrderEntity.fromDomain(order));

        outboxService.saveEvent(
                "Order",
                orderId,
                "OrderCreated",
                new OrderCreatedEvent(orderId, command.getUserId(), command.getAmount())
        );


        return orderId;
    }

    @Override
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(OrderEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @Override
    public Iterable<Order> getOrdersByUserId(UUID userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderEntity::toDomain)
                .toList();
    }
}
package com.orders.adapter;

import com.orders.application.command.CreateOrderCommand;
import com.orders.application.service.OrderService;
import com.orders.domain.Order;
import com.orders.dto.CreateOrderRequest;
import com.orders.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "API для управления заказами")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Создать новый заказ")
    public UUID createOrder(@RequestBody CreateOrderRequest request) throws Exception {
        return orderService.createOrder(new CreateOrderCommand(
                request.getUserId(),
                request.getAmount(),
                request.getDescription()
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить заказ по ID")
    public OrderResponse getOrderById(@PathVariable UUID id) {
        return OrderResponse.fromDomain(orderService.getOrderById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Получить список заказов пользователя")
    public List<OrderResponse> getOrdersByUserId(@PathVariable UUID userId) {
        Iterable<Order> orders = orderService.getOrdersByUserId(userId);

        return StreamSupport.stream(orders.spliterator(), false)
                .map(OrderResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
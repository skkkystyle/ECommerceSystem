package com.orders.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateOrderCommand {
    private UUID userId;
    private BigDecimal amount;
    private String description;
}
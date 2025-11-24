package com.orders.application.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCreatedEvent {

    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;

    public static OrderCreatedEvent fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, OrderCreatedEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize OrderCreatedEvent", e);
        }
    }
}

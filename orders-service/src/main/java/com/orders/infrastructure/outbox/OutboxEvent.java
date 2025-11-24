package com.orders.infrastructure.outbox;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

    @Id
    @GeneratedValue
    private UUID id;

    private String aggregateType;
    private UUID aggregateId;

    private String eventType;

    @Lob
    private String payload;

    private LocalDateTime createdAt;

    private boolean processed;
}

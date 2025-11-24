package com.payments.infrastructure.inbox;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inbox_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InboxEvent {
    @Id
    private UUID id;
    private String eventType;
    private String payload;
    private LocalDateTime occurredOn;
    private Boolean processed = false;
}
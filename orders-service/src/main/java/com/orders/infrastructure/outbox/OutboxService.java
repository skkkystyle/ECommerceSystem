package com.orders.infrastructure.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public void saveEvent(String aggregateType, UUID aggregateId, String eventType, Object payload) throws Exception {
        String jsonPayload = objectMapper.writeValueAsString(payload);
        OutboxEvent event = OutboxEvent.builder()
                .aggregateType(aggregateType)
                .aggregateId(aggregateId)
                .eventType(eventType)
                .payload(jsonPayload)
                .processed(false)
                .createdAt(LocalDateTime.now())
                .build();

        outboxRepository.save(event);
    }
}

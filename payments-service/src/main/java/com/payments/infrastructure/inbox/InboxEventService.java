package com.payments.infrastructure.inbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InboxEventService {

    private final InboxEventRepository inboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveEvent(String eventType, Object payload) throws JsonProcessingException {
        String jsonPayload = objectMapper.writeValueAsString(payload);

        InboxEvent event = InboxEvent.builder()
                .id(UUID.randomUUID())
                .eventType(eventType)
                .payload(jsonPayload)
                .occurredOn(java.time.LocalDateTime.now())
                .processed(false)
                .build();

        inboxEventRepository.save(event);
    }
}
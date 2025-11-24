package com.orders.infrastructure.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxProcessor {

    private final OutboxEventRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    public void processOutboxEvents() {
        List<OutboxEvent> events = outboxRepository.findAllByProcessedFalse();

        for (OutboxEvent event : events) {
            try {
                rabbitTemplate.convertAndSend("order.created", event.getPayload());
                event.setProcessed(true);
                outboxRepository.save(event);
                log.info("Sent event: {}", event.getEventType());
            } catch (Exception e) {
                log.error("Failed to send event: {}", event.getId(), e);
            }
        }
    }
}

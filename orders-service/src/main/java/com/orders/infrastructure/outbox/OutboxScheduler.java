package com.orders.infrastructure.outbox;

import com.orders.application.event.OrderCreatedEvent;
import com.orders.application.event.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final OutboxEventRepository outboxEventRepository;
    private final OrderEventPublisher eventPublisher;

    @Scheduled(fixedDelay = 5000)
    public void processOutboxEvents() {
        List<OutboxEvent> events = outboxEventRepository.findTop100ByProcessedFalseOrderByCreatedAtAsc();

        for (OutboxEvent event : events) {
            try {
                OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.fromJson(event.getPayload());
                eventPublisher.publish(orderCreatedEvent);
                event.setProcessed(true);
                outboxEventRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
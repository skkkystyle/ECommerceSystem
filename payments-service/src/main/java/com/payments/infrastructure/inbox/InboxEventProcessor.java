package com.payments.infrastructure.inbox;

import com.payments.application.event.OrderCreatedEvent;
import com.payments.application.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InboxEventProcessor {

    private final InboxEventRepository inboxEventRepository;
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    public void processInboxEvents() {
        List<InboxEvent> events = inboxEventRepository.findTop100ByProcessedFalse();

        for (InboxEvent event : events) {
            try {
                if ("OrderCreated".equals(event.getEventType())) {
                    OrderCreatedEvent orderEvent = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);
                    paymentService.processOrderPayment(orderEvent);
                }

                event.setProcessed(true);
                inboxEventRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
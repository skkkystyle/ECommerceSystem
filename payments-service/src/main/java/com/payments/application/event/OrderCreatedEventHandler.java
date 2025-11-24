package com.payments.application.event;

import com.payments.infrastructure.inbox.InboxEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedEventHandler {

    private final InboxEventService inboxEventService;

    @RabbitListener(queues = "order.created")
    public void handle(OrderCreatedEvent event) throws Exception {
        inboxEventService.saveEvent("OrderCreated", event);
    }
}
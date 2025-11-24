package com.payments.application.service;

import com.payments.application.event.OrderCreatedEvent;
import com.payments.domain.Account;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {
    @Transactional
    UUID createAccount(UUID userId);

    @Transactional
    void topUpAccount(UUID accountId, BigDecimal amount);

    @Transactional
    void processOrderPayment(OrderCreatedEvent event);

    Account getAccountById(UUID accountId);
}

package com.payments.application.service;

import com.payments.application.command.CreateAccountCommand;
import com.payments.application.command.CreateAccountUseCase;
import com.payments.application.event.OrderCreatedEvent;
import com.payments.domain.Account;
import com.payments.infrastructure.repository.AccountEntity;
import com.payments.infrastructure.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final CreateAccountUseCase createAccountUseCase;
    private final AccountJpaRepository accountRepository;

    @Transactional
    @Override
    public UUID createAccount(UUID userId) {
        return createAccountUseCase.handle(new CreateAccountCommand(userId));
    }

    @Transactional
    @Override
    public void topUpAccount(UUID accountId, BigDecimal amount) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void processOrderPayment(OrderCreatedEvent event) {
        AccountEntity account = accountRepository.findByUserId(event.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (account.getBalance().compareTo(event.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(event.getAmount()));
        accountRepository.save(account);
    }

    @Override
    public Account getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
                .map(AccountEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }
}
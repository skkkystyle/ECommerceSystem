package com.payments.application.command;

import com.payments.domain.Balance;
import com.payments.infrastructure.repository.AccountEntity;
import com.payments.infrastructure.repository.AccountJpaRepository;
import com.payments.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountJpaRepository accountRepository;

    @Transactional
    public UUID handle(CreateAccountCommand command) {
        if (accountRepository.findByUserId(command.getUserId()).isPresent()) {
            throw new IllegalArgumentException("User already has an account");
        }

        Account account = Account.builder()
                .id(UUID.randomUUID())
                .userId(command.getUserId())
                .balance(new Balance(BigDecimal.ZERO))
                .createdAt(LocalDateTime.now())
                .build();

        accountRepository.save(AccountEntity.fromDomain(account));

        return account.getId();
    }
}
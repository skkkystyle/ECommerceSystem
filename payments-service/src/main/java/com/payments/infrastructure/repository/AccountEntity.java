package com.payments.infrastructure.repository;

import com.payments.domain.Account;
import com.payments.domain.Balance;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {
    @Id
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    public static AccountEntity fromDomain(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .userId(account.getUserId())
                .balance(account.getBalance().getAmount())
                .createdAt(account.getCreatedAt())
                .build();
    }

    public Account toDomain() {
        return Account.builder()
                .id(id)
                .userId(userId)
                .balance(new Balance(balance))
                .createdAt(createdAt)
                .build();
    }
}
package com.payments.dto;

import com.payments.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    public static AccountResponse fromDomain(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getUserId(),
                account.getBalance().getAmount(),
                account.getCreatedAt()
        );
    }
}
package com.payments.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
    private BigDecimal amount;

    public Balance add(Balance other) {
        return new Balance(this.amount.add(other.amount));
    }

    public Balance subtract(Balance other) {
        return new Balance(this.amount.subtract(other.amount));
    }
}
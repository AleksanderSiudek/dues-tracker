package com.duetracker.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Payment(Long idOfMember, BigDecimal amount, LocalDate date) {
    public Payment {
        if (idOfMember == null || idOfMember < 0) {
            throw new IllegalArgumentException("Id of member cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }
}
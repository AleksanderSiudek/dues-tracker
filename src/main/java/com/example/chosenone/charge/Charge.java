package com.example.chosenone.charge;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Charge(Long idOfMember, BigDecimal amount, LocalDate dueDate, String title) {
    public Charge {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater then zero");
        }
        if (idOfMember == null || idOfMember < 0) {
            throw new IllegalArgumentException("Id cannot be empty or null or is under 0");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
    }
}

package com.duetracker.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentRequest(Long idOfMember, BigDecimal amount, LocalDate date) {
}

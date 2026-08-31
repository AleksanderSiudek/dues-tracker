package com.duetracker.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentResponse(Long idOfMember, BigDecimal amount, LocalDate date) {
}

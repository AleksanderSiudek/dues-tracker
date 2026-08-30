package com.duetracker.charge;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChargeResponse(Long idOfMember, BigDecimal amount, LocalDate dueDate, String title) {
}
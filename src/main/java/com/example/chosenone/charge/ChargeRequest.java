package com.example.chosenone.charge;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChargeRequest(Long idOfMember, BigDecimal amount, LocalDate dueDate, String title) {
}
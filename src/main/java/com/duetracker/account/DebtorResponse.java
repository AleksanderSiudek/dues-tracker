package com.duetracker.account;

import java.math.BigDecimal;

public record DebtorResponse(Long id, String fullName, BigDecimal balance) {
}

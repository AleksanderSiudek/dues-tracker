package com.duetracker.account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MembershipAccountServiceInterface {
        BigDecimal balance(Long memberId, LocalDate asOf);

        boolean isSettled(Long memberId, LocalDate asOf);

        List<Long> debtors(LocalDate asOf);

        BigDecimal totalDebt(LocalDate asOf);

        BigDecimal lateFee(Long memberId, LocalDate asOf);
}
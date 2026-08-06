package com.example.chosenone;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MembershipAccountServiceInterface {

        BigDecimal balance(Long memberId,
                        List<Charge> charges,
                        List<Payment> payments,
                        LocalDate asOf);

        boolean isSettled(Long memberId,
                        List<Charge> charges,
                        List<Payment> payments,
                        LocalDate asOf);

        List<Long> debtors(List<Charge> charges, List<Payment> payments, LocalDate asOf);

        BigDecimal totalDebt(List<Charge> charges, List<Payment> payments, LocalDate asOf);

        BigDecimal lateFee(Long memberId, List<Charge> charges, List<Payment> payments, LocalDate asOf);
}
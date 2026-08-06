package com.example.chosenone;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MemberShipAccountServiceTest {
    private final MembershipAccountServiceInterface service = new MembershipAccountService();

    private final List<Charge> charges = List.of(
            new Charge(1L, new BigDecimal("10.00"), LocalDate.of(2026, 7, 3), "payment 7/2026"),
            new Charge(2L, new BigDecimal("10.00"), LocalDate.of(2026, 7, 3), "czynsz 07/2026"));
    private final List<Payment> payments = List.of(
            new Payment(1L, new BigDecimal("10.00"), LocalDate.of(2026, 7, 2)),
            new Payment(2L, new BigDecimal("7.00"), LocalDate.of(2026, 7, 2)));
    private final LocalDate asOf = LocalDate.of(2026, 7, 3);

    @Test
    void balanceIsZeroWhenMemberPaidExactly() {
        var result = service.balance(1L, charges, payments, asOf);

        // uwaga: NIE assertEquals(BigDecimal.ZERO, result) - pamiętasz pułapkę
        // equals/compareTo?
        // ZERO ma skalę 0, a Twój wynik 0.00 skalę 2 - equals dałby false!
        assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void memberWithUnderpaymentIsNotSettled() {
        var result = service.isSettled(2L, charges, payments, asOf);

        assertThat(result).isFalse();
    }

    @Test
    void memberWithExactPaymentIsSettled() {
        var result = service.isSettled(1L, charges, payments, asOf);

        assertThat(result).isTrue();
    }

    @Test
    void balanceIsNegativeAfterUnderpayment() {
        var result = service.balance(2L, charges, payments, asOf);

        assertThat(result).isEqualByComparingTo("-3.00");
    }

    @Test
    void debtorsReturnsOnlyMembersWithNegativeBalance() {
        var result = service.debtors(charges, payments, asOf);

        assertThat(result).containsExactly(2L);
    }

    @Test
    void totalDebtSumsAllNegativeBalances() {
        var result = service.totalDebt(charges, payments, asOf);

        assertThat(result).isEqualByComparingTo("-3.00");
    }

    @Test
    void lateFeeIsChargedOnOverdueAmount() {
        var result = service.lateFee(2L, charges, payments, asOf);
        // członek 2 ma dług 3.00, odsetki 5% => 0.15
        assertThat(result).isEqualByComparingTo("0.15");
    }
}

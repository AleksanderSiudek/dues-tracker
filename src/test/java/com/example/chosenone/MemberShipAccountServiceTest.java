package com.example.chosenone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberShipAccountServiceTest {
    private final InMemoryChargeRepository chargeRepository = new InMemoryChargeRepository();
    private final InMemoryPaymentRepository paymentRepository = new InMemoryPaymentRepository();
    private final InMemoryMemberRepository memberRepository = new InMemoryMemberRepository();
    private final MembershipAccountService service = new MembershipAccountService(memberRepository, chargeRepository,
            paymentRepository);

    private final LocalDate asOf = LocalDate.of(2026, 8, 3);

    @BeforeEach
    void setUp() {
        chargeRepository.save(new Charge(1L, new BigDecimal("10.00"), LocalDate.of(2026, 7, 3), "czynsz"));
        chargeRepository.save(new Charge(2L, new BigDecimal("10.00"), LocalDate.of(2026, 7, 3), "czynsz"));
        paymentRepository.save(new Payment(1L, new BigDecimal("10.00"), LocalDate.of(2026, 7, 2)));
        paymentRepository.save(new Payment(2L, new BigDecimal("7.00"), LocalDate.of(2026, 7, 2)));
    }

    @Test
    void balanceIsZeroWhenMemberPaidExactly() {
        BigDecimal result = service.balance(1L, asOf);

        // uwaga: NIE assertEquals(BigDecimal.ZERO, result) - pamiętasz pułapkę
        // equals/compareTo?
        // ZERO ma skalę 0, a Twój wynik 0.00 skalę 2 - equals dałby false!
        assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void memberWithUnderpaymentIsNotSettled() {
        var result = service.isSettled(2L, asOf);

        assertThat(result).isFalse();
    }

    @Test
    void memberWithExactPaymentIsSettled() {
        var result = service.isSettled(1L, asOf);

        assertThat(result).isTrue();
    }

    @Test
    void balanceIsNegativeAfterUnderpayment() {
        var result = service.balance(2L, asOf);

        assertThat(result).isEqualByComparingTo("-3.00");
    }

    @Test
    void debtorsReturnsOnlyMembersWithNegativeBalance() {
        var result = service.debtors(asOf);

        assertThat(result).containsExactly(2L);
    }

    @Test
    void totalDebtSumsAllNegativeBalances() {
        var result = service.totalDebt(asOf);

        assertThat(result).isEqualByComparingTo("-3.00");
    }

    @Test
    void lateFeeIsChargedOnOverdueAmount() {
        var result = service.lateFee(2L, asOf);
        // członek 2 ma dług 3.00, odsetki 5% => 0.15
        assertThat(result).isEqualByComparingTo("0.15");
    }
}

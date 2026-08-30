package com.example.chosenone;

import com.example.chosenone.account.MembershipAccountService;
import com.example.chosenone.charge.Charge;
import com.example.chosenone.charge.ChargeRepository;
import com.example.chosenone.member.Member;
import com.example.chosenone.member.MemberRepository;
import com.example.chosenone.payment.Payment;
import com.example.chosenone.payment.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberShipAccountServiceTest extends AbstractIntegrationTest {
    private final LocalDate asOf = LocalDate.of(2026, 8, 3);
    @Autowired
    private MembershipAccountService service;
    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        chargeRepository.deleteAll();
        paymentRepository.deleteAll();
        memberRepository.deleteAll();
        memberRepository.save(new Member(1L, "Jan Kowalski"));
        memberRepository.save(new Member(2L, "Anna Nowak"));
        chargeRepository.save(new Charge(1L, new BigDecimal("10.00"), LocalDate.of(2026, 7, 3), "payment"));
        chargeRepository.save(new Charge(2L, new BigDecimal("10.00"), LocalDate.of(2026, 7, 3), "payment"));
        paymentRepository.save(new Payment(1L, new BigDecimal("10.00"), LocalDate.of(2026, 7, 2)));
        paymentRepository.save(new Payment(2L, new BigDecimal("7.00"), LocalDate.of(2026, 7, 2)));
    }

    @Test
    void balanceIsZeroWhenMemberPaidExactly() {
        BigDecimal result = service.balance(1L, asOf);
        // Do not use assertEquals(BigDecimal.ZERO, result):
        // equals() also compares scale, so 0 and 0.00 are not equal.
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
        // Member 2 has debt 3.00; 5% late fee equals 0.15.
        assertThat(result).isEqualByComparingTo("0.15");
    }
}

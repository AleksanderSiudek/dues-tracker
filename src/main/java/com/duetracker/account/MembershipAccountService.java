package com.duetracker.account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.duetracker.charge.Charge;
import com.duetracker.charge.ChargeRepository;
import com.duetracker.error.MemberNotFoundException;
import com.duetracker.member.MemberRepository;
import com.duetracker.payment.Payment;
import com.duetracker.payment.PaymentRepository;

@Service
public class MembershipAccountService implements MembershipAccountServiceInterface {
    private final MemberRepository memberRepository;
    private final ChargeRepository chargeRepository;
    private final PaymentRepository paymentRepository;

    public MembershipAccountService(MemberRepository memberRepository, ChargeRepository chargeRepository, PaymentRepository paymentRepository) {
        this.memberRepository = memberRepository;
        this.chargeRepository = chargeRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public BigDecimal balance(Long memberId, LocalDate asOf) {
        if (memberRepository.findById(memberId).isEmpty()) {
            throw new MemberNotFoundException("Member not found: " + memberId);
        }
        var charges = chargeRepository.findByMember(memberId);
        var payments = paymentRepository.findByMember(memberId);
        var totalPayment = payments.stream().filter(payment -> !payment.date().isAfter(asOf)).map(Payment::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
        var totalCharge = charges.stream().filter(charge -> !charge.dueDate().isAfter(asOf)).map(Charge::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPayment.subtract(totalCharge);
    }

    @Override
    public boolean isSettled(Long memberId, LocalDate asOf) {
        return balance(memberId, asOf).compareTo(BigDecimal.ZERO) >= 0;
    }

    @Override
    public List<Long> debtors(LocalDate asOf) {
        var debtorsId = chargeRepository.findAll().stream().map(charge -> charge.idOfMember()).distinct()
                .filter(idOfMember -> balance(idOfMember, asOf).compareTo(BigDecimal.ZERO) < 0).toList();
        return debtorsId;
    }

    @Override
    public BigDecimal totalDebt(LocalDate asOf) {
        var debtorIds = debtors(asOf);
        var sum = debtorIds.stream().map(memberId -> balance(memberId, asOf)).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;
    }

    @Override
    public BigDecimal lateFee(Long memberId, LocalDate asOf) {
        var balance = balance(memberId, asOf);
        if (balance.compareTo(BigDecimal.ZERO) >= 0) {
            return BigDecimal.ZERO;
        }
        var debt = balance.negate();
        var oldestDue = chargeRepository.findByMember(memberId).stream().map(Charge::dueDate).min(LocalDate::compareTo).orElseThrow();
        var monthsLate = ChronoUnit.MONTHS.between(oldestDue, asOf);
        var changedMonthsLate = BigDecimal.valueOf(monthsLate);
        return debt.multiply(new BigDecimal("0.05")).multiply(changedMonthsLate);
    }
}
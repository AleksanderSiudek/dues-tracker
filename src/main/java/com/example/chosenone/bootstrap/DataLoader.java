package com.example.chosenone.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.chosenone.charge.Charge;
import com.example.chosenone.charge.ChargeRepository;
import com.example.chosenone.member.Member;
import com.example.chosenone.member.MemberRepository;
import com.example.chosenone.payment.Payment;
import com.example.chosenone.payment.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final ChargeRepository chargeRepository;
    private final PaymentRepository paymentRepository;

    public DataLoader(ChargeRepository chargeRepository, PaymentRepository paymentRepository, MemberRepository memberRepository) {
        this.chargeRepository = chargeRepository;
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) {
        if (!memberRepository.findAll().isEmpty()) {
            return; // Data already exists; do not load again.
        }
        // Member 1 paid in full and will not be a debtor.
        memberRepository.save(new Member(1L, "Jan Kowalski"));
        chargeRepository.save(new Charge(1L, new BigDecimal("100.00"), LocalDate.of(2026, 7, 1), "rent 07/2026"));
        paymentRepository.save(new Payment(1L, new BigDecimal("100.00"), LocalDate.of(2026, 7, 5)));
        // Member 2 underpaid and will be a debtor.
        memberRepository.save(new Member(2L, "Anna Nowak"));
        chargeRepository.save(new Charge(2L, new BigDecimal("100.00"), LocalDate.of(2026, 7, 1), "rent 07/2026"));
        paymentRepository.save(new Payment(2L, new BigDecimal("60.00"), LocalDate.of(2026, 7, 5)));
    }
}
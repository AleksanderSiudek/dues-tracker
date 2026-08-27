package com.example.chosenone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
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
            return; // dane już są - nie ładuj ponownie
        }
        // członek 1 - zapłacił co do grosza (nie będzie dłużnikiem)
        memberRepository.save(new Member(1L, "Jan Kowalski"));
        chargeRepository.save(new Charge(1L, new BigDecimal("100.00"), LocalDate.of(2026, 7, 1), "czynsz 07/2026"));
        paymentRepository.save(new Payment(1L, new BigDecimal("100.00"), LocalDate.of(2026, 7, 5)));
        // członek 2 - niedopłacił (BĘDZIE dłużnikiem)
        memberRepository.save(new Member(2L, "Anna Nowak"));
        chargeRepository.save(new Charge(2L, new BigDecimal("100.00"), LocalDate.of(2026, 7, 1), "czynsz 07/2026"));
        paymentRepository.save(new Payment(2L, new BigDecimal("60.00"), LocalDate.of(2026, 7, 5)));
    }
}
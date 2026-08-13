package com.example.chosenone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final InMemoryChargeRepository chargeRepository;
    private final InMemoryPaymentRepository paymentRepository;
    private final InMemoryMemberRepository memberRepository;

    public DataLoader(InMemoryChargeRepository chargeRepository, InMemoryPaymentRepository paymentRepository, InMemoryMemberRepository memberRepository) {
        this.chargeRepository = chargeRepository;
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) {
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
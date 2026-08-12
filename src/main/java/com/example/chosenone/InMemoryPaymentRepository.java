package com.example.chosenone;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPaymentRepository {
    ArrayList<Payment> payments = new ArrayList<>();

    void save(Payment payment) {
        payments.add(payment);
    }

    List<Payment> findByMember(Long memberId) {
        return payments.stream().filter(payment -> payment.idOfMember().equals(memberId)).toList();
    }

    List<Payment> findAll() {
        return payments;
    }
}

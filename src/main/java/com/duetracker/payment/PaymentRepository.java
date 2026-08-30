package com.duetracker.payment;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {
    private final PaymentRepositoryJpa jpa;

    public PaymentRepository(PaymentRepositoryJpa jpa) {
        this.jpa = jpa;
    }

    public void deleteAll() {
        jpa.deleteAll();
    }

    public void save(Payment payment) {
        jpa.save(PaymentMapper.toEntity(payment));
    }

    public List<Payment> findByMember(Long memberId) {
        return jpa.findByIdOfMember(memberId).stream().map(PaymentMapper::toDomain).toList();
        // return findAll().stream().filter(payment ->
        // payment.idOfMember().equals(memberId)).toList();
    }

    public List<Payment> findAll() {
        return jpa.findAll().stream().map(PaymentMapper::toDomain).toList();
    }
}
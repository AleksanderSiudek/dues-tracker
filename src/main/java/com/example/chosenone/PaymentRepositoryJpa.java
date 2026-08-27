package com.example.chosenone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepositoryJpa extends JpaRepository<PaymentEntity, Long> {
}

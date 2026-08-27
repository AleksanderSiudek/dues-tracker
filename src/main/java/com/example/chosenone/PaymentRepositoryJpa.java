package com.example.chosenone;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepositoryJpa extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByIdOfMember(Long idOfMember);
}

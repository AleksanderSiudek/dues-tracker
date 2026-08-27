package com.example.chosenone;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChargeRepositoryJpa extends JpaRepository<ChargeEntity, Long> {
    List<ChargeEntity> findByIdOfMember(Long idOfMember);
}

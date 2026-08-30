package com.example.chosenone.charge;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ChargeRepository {
    private final ChargeRepositoryJpa jpa;

    public ChargeRepository(ChargeRepositoryJpa jpa) {
        this.jpa = jpa;
    }

    public void deleteAll() {
        jpa.deleteAll();
    }

    public void save(Charge charge) {
        jpa.save(ChargeMapper.toEntity(charge));
    }

    public List<Charge> findByMember(Long memberId) {
        return jpa.findByIdOfMember(memberId).stream().map(ChargeMapper::toDomain).toList();
        // return findAll().stream().filter(charge ->
        // charge.idOfMember().equals(memberId)).toList();
        // filtering can also be done directly via query method
    }

    public List<Charge> findAll() {
        return jpa.findAll().stream().map(ChargeMapper::toDomain).toList();
        // entity -> ChargeMapper.toDomain(entity) is equivalent form
    }
}

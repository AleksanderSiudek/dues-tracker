package com.example.chosenone;

import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class MemberRepository {
    private final MemberRepositoryJpa jpa;

    public MemberRepository(MemberRepositoryJpa jpa) {
        this.jpa = jpa;
    }

    public void deleteAll() { // <- usuń całość z DataLoadera
        jpa.deleteAll();
    }

    public void save(Member member) {
        jpa.save(MemberMapper.toEntity(member));
    }

    public Optional<Member> findById(Long id) {
        return jpa.findById(id).map(MemberMapper::toDomain);
    }
}
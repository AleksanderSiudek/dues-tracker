package com.example.chosenone;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private final MemberRepositoryJpa jpa;

    public MemberRepository(MemberRepositoryJpa jpa) {
        this.jpa = jpa;
    }

    public void deleteAll() {
        jpa.deleteAll();
    }

    public List<Member> findAll() {
        return jpa.findAll().stream().map(MemberMapper::toDomain).toList();
    }

    public void save(Member member) {
        jpa.save(MemberMapper.toEntity(member));
    }

    public Optional<Member> findById(Long id) {
        return jpa.findById(id).map(MemberMapper::toDomain);
    }
}
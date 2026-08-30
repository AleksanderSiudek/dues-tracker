package com.example.chosenone.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryJpa extends JpaRepository<MemberEntity, Long> {
}
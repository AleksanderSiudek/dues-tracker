package com.duetracker.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryJpa extends JpaRepository<MemberEntity, Long> {
}
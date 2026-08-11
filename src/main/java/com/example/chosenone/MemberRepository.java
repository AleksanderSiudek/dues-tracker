package com.example.chosenone;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;
@Repository
public class MemberRepository {
    private final Map<Long, Member> members = new HashMap<>();

    public void save(Member member) {
        members.put(member.getId(), member);
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(members.get(id));

    }
}

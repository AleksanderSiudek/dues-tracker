package com.example.chosenone.member;

import java.util.Objects;

public class Member {
    private final Long id; // e.g. apartment number or UUID
    private final String fullName;

    public Member(Long id, String fullName) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Id cannot be empty or null");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be empty or null");
        }
        this.id = id;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        var member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
package com.example.chosenone.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MemberEntity {
    @Id
    private Long id;
    private String fullName;

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

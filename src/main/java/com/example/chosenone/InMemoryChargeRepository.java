package com.example.chosenone;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryChargeRepository {
    ArrayList<Charge> list = new ArrayList<>();

    void save(Charge charge) {
        list.add(charge);
    }

    List<Charge> findByMember(Long memberId) {
        return list.stream().filter(charge -> charge.idOfMember().equals(memberId)).toList();
    }

    List<Charge> findAll() {
        return list;
    }
}

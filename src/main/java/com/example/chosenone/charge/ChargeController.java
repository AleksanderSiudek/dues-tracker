package com.example.chosenone.charge;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/charges")
public class ChargeController {
    private final ChargeRepository chargeRepository;

    public ChargeController(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    @GetMapping
    public List<Charge> getAllCharges() {
        return chargeRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Charge createList(@RequestBody Charge charges) {
        chargeRepository.save(charges);
        return charges;
    }
}

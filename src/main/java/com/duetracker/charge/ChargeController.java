package com.duetracker.charge;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.duetracker.error.MemberNotFoundException;
import com.duetracker.member.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/charges")
public class ChargeController {
    private final ChargeRepository chargeRepository;
    private final MemberRepository memberRepository;

    public ChargeController(ChargeRepository chargeRepository, MemberRepository memberRepository) {
        this.chargeRepository = chargeRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public List<ChargeResponse> getAllCharges() {
        return chargeRepository.findAll().stream().map(ChargeDtoMapper::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChargeResponse createCharge(@RequestBody ChargeRequest request) {
        if (memberRepository.findById(request.idOfMember()).isEmpty()) {
            throw new MemberNotFoundException("Member not found: " + request.idOfMember());
        }
        var charge = ChargeDtoMapper.toDomain(request);
        chargeRepository.save(charge);
        return ChargeDtoMapper.toResponse(charge);
    }
}

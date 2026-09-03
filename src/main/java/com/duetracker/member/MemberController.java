package com.duetracker.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.duetracker.account.MembershipAccountService;
import com.duetracker.error.MemberAlreadyExistsException;
import com.duetracker.error.MemberNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MembershipAccountService service;

    public MemberController(MemberRepository memberRepository, MembershipAccountService service) {
        this.memberRepository = memberRepository;
        this.service = service;
    }

    @GetMapping
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream().map(MemberDtoMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable Long id) {
        return memberRepository.findById(id).map(MemberDtoMapper::toResponse).orElseThrow(() -> new MemberNotFoundException("Member not found: " + id));
    }

    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {
        return service.balance(id, LocalDate.now());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse createMember(@RequestBody MemberRequest request) {
        if (memberRepository.findById(request.id()).isPresent()) {
            throw new MemberAlreadyExistsException("Member already exists: " + request.id());
        }
        var member = MemberDtoMapper.toDomain(request);
        memberRepository.save(member);
        return MemberDtoMapper.toResponse(member);
    }
}

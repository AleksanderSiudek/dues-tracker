package com.duetracker.account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duetracker.member.Member;
import com.duetracker.member.MemberRepository;

@RestController
@RequestMapping("/debtors")
public class DebtorController {

    private final MembershipAccountService membershipAccountService;
    private final MemberRepository memberRepository;

    public DebtorController(MembershipAccountService membershipAccountService,
            MemberRepository memberRepository) {
        this.membershipAccountService = membershipAccountService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public List<DebtorResponse> getDebtors(@RequestParam(required = false) LocalDate asOf) {

        LocalDate date = (asOf == null) ? LocalDate.now() : asOf;

        return membershipAccountService.debtors(date).stream().map(id -> new DebtorResponse(
                id,
                memberRepository.findById(id).map(Member::getFullName).orElseThrow(),
                membershipAccountService.balance(id, date)
        )).toList();
    }

    @GetMapping("/total")
    public BigDecimal totalDebtors(@RequestParam(required = false) LocalDate asOf) {
        LocalDate date = (asOf == null) ? LocalDate.now() : asOf;
        return membershipAccountService.totalDebt(date);

    }

}

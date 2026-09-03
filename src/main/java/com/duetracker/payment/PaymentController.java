package com.duetracker.payment;

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
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    public PaymentController(PaymentRepository paymentRepository, MemberRepository memberRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream().map(PaymentDtoMapper::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@RequestBody PaymentRequest request) {
        if (memberRepository.findById(request.idOfMember()).isEmpty()) {
            throw new MemberNotFoundException("Member not found: " + request.idOfMember());
        }
        var payment = PaymentDtoMapper.toDomain(request);
        paymentRepository.save(payment);
        return PaymentDtoMapper.toResponse(payment);
    }
}

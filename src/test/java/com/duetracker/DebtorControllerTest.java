package com.duetracker;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.duetracker.charge.Charge;
import com.duetracker.charge.ChargeRepository;
import com.duetracker.member.Member;
import com.duetracker.member.MemberRepository;
import com.duetracker.payment.Payment;
import com.duetracker.payment.PaymentRepository;

class DebtorControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        chargeRepository.deleteAll();
        paymentRepository.deleteAll();
        memberRepository.deleteAll();
        memberRepository.save(new Member(2L, "Anna Nowak"));
        chargeRepository.save(new Charge(2L, new BigDecimal("100.00"), LocalDate.now(), "payment"));
        paymentRepository.save(new Payment(2L, new BigDecimal("60.00"), LocalDate.now()));
    }

    @Test
    void returnDebtorsWithBalance() throws Exception {
        mockMvc.perform(get("/debtors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Anna Nowak"))
                .andExpect(jsonPath("$[0].balance").value(-40.00));
    }
}

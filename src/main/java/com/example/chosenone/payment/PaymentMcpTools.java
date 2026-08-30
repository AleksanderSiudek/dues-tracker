package com.example.chosenone.payment;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Service;

import com.example.chosenone.account.MembershipAccountService;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentMcpTools {
    private final MembershipAccountService service;

    public PaymentMcpTools(MembershipAccountService service) {
        this.service = service;
    }

    @McpTool(description = "Returns the list of member IDs who currently owe money (have a negative balance)")
    public List<Long> getDebtors() {
        return service.debtors(LocalDate.now());
    }
}
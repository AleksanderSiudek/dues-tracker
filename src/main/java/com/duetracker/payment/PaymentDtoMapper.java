package com.duetracker.payment;

public class PaymentDtoMapper {
    public static Payment toDomain(PaymentRequest request) {
        return new Payment(request.idOfMember(), request.amount(), request.date());
    }

    public static PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(payment.idOfMember(), payment.amount(), payment.date());
    }
}

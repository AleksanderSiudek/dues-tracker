package com.example.chosenone;

public class PaymentMapper {
    public static PaymentEntity toEntity(Payment payment) {
        var result = new PaymentEntity();
        result.setIdOfMember(payment.idOfMember());
        result.setAmount(payment.amount());
        result.setDate(payment.date());
        return result;
    }

    public static Payment toDomain(PaymentEntity entity) {
        return new Payment(entity.getIdOfMember(), entity.getAmount(), entity.getDate());
    }
}

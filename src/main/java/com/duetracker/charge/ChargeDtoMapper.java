package com.duetracker.charge;

public class ChargeDtoMapper {
    public static Charge toDomain(ChargeRequest request) {
        return new Charge(request.idOfMember(), request.amount(), request.dueDate(), request.title());
    }

    public static ChargeResponse toResponse(Charge charge) {
        return new ChargeResponse(charge.idOfMember(), charge.amount(), charge.dueDate(), charge.title());
    }
}

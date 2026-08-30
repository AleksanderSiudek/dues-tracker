package com.example.chosenone.charge;

public class ChargeMapper {
    public static ChargeEntity toEntity(Charge charge) {
        var result = new ChargeEntity();
        result.setIdOfMember(charge.idOfMember());
        result.setAmount(charge.amount());
        result.setDueDate(charge.dueDate());
        result.setTitle(charge.title());
        return result;
    }

    public static Charge toDomain(ChargeEntity entity) {
        return new Charge(entity.getIdOfMember(), entity.getAmount(), entity.getDueDate(), entity.getTitle());
    }
}

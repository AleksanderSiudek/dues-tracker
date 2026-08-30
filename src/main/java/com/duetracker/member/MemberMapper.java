package com.duetracker.member;

public class MemberMapper {
    public static MemberEntity toEntity(Member member) {
        var result = new MemberEntity();
        result.setId(member.getId());
        result.setFullName(member.getFullName());
        return result;
    }

    public static Member toDomain(MemberEntity entity) {
        return new Member(entity.getId(), entity.getFullName());
    }
}

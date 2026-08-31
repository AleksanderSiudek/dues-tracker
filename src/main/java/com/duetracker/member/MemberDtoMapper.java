package com.duetracker.member;

public class MemberDtoMapper {
    public static Member toDomain(MemberRequest request) {
        return new Member(request.id(), request.fullName());
    }

    public static MemberResponse toResponse(Member member) {
        return new MemberResponse(member.getId(), member.getFullName());
    }
}

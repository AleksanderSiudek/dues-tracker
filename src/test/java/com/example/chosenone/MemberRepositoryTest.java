package com.example.chosenone;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberRepositoryTest {
    @Test
    void testFindById() {
        // given
        var repo = new MemberRepository();
        var member = new Member(1L, "Achilles");
        // when
        repo.save(member);
        var result = repo.findById(member.getId());
        // then
        Assertions.assertThat(result).isEqualTo(Optional.of(member));
    }

    @Test
    void returnsEmptyWhenMemberNotFound() {
        var repo = new MemberRepository();

        var result = repo.findById(999L);

        Assertions.assertThat(result).isEmpty();
    }

}

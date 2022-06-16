package com.example.assignmentpm.collection.infrastructure.dynamoRepository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.assignmentpm.collection.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Member가 저장된다.")
    void save() {
        //given
        String name = "이름";
        String email = "kbhxxx@gmail.com";
        String hp = "+8201045556000";

        //when
        memberRepository.save(Member.builder()
                        .name(name)
                        .hp(hp)
                        .email(email).build());

        //then
        Member saveMember = memberRepository.findAll().iterator().next();
        assertThat(saveMember.getEmail()).isEqualTo(email);
        assertThat(saveMember.getName()).isEqualTo(name);
    }

}

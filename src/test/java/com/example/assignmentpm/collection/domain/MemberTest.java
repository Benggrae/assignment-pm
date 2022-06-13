package com.example.assignmentpm.collection.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    @DisplayName("휴대폰번호 유효성 검사")
    void hpValid() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                Member.builder().name("테스트")
                   .email("kkk@gmail.com")
                   .hp("")
                   .build()
        );
    }

    @Test
    @DisplayName("이메일주소 유효성 검사")
    void emailValid() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                Member.builder().name("테스트")
                        .email("kkkgmail.com")
                        .hp("8201033370581")
                        .build()
        );
    }

}
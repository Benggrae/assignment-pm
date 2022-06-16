package com.example.assignmentpm.collection.dto;

import com.example.assignmentpm.collection.domain.Member;
import java.util.Optional;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRequest {

    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String hp;
    @NotEmpty
    private String agreeYn;

    @Builder
    public MemberRequest(String email, String name, String hp, String agreeYn) {
        this.email = email;
        this.name = name;
        this.hp = hp;
        this.agreeYn = agreeYn;
    }

    public Optional<Member> toMember() {
        if (!"Y".equals(agreeYn)) {
            return Optional.empty();
        }
        return Optional.of(Member.builder()
                .email(email)
                .hp(hp)
                .name(name).build());
    }
}

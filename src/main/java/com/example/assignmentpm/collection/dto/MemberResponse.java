package com.example.assignmentpm.collection.dto;

import com.example.assignmentpm.collection.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponse {
    private String id;
    private String name;
    private String email;
    private String hp;

    public static MemberResponse buildResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .hp(member.getHp())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}

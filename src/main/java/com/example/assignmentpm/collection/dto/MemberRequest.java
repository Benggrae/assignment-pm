package com.example.assignmentpm.collection.dto;

import com.example.assignmentpm.collection.domain.Member;
import lombok.Data;

@Data
public class MemberRequest {

    private String id;
    private String name;

    public Member toMember() {
        return Member.builder().name(this.name)
                .id(this.id)
                .build();
    }
}

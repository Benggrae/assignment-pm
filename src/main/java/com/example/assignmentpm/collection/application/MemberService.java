package com.example.assignmentpm.collection.application;

import com.example.assignmentpm.collection.domain.Member;
import com.example.assignmentpm.collection.dto.MemberRequest;
import com.example.assignmentpm.collection.infrastructure.dynamoRepository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final IllegalArgumentException IS_PROCESS_EXCEPTION = new IllegalArgumentException("이미 참여하였습니다.");
    private final MemberRepository memberRepository;

    private final MemberSearchService memberSearchService;


    public String saveMember(MemberRequest request) {
        final Optional<Member> optionalMember = request.toMember();
        if (optionalMember.isEmpty()) {
            return null;
        }
        if (memberSearchService.hasEmailAndHp(optionalMember.get()).isPresent()) {
            throw IS_PROCESS_EXCEPTION;
        }

        return memberRepository.save(optionalMember.get()).getId();
    }
}

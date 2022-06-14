package com.example.assignmentpm.collection.application;

import com.example.assignmentpm.collection.dto.MemberRequest;
import com.example.assignmentpm.collection.dto.MemberResponse;
import com.example.assignmentpm.collection.infrastructure.dynamoRepository.MemberRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String saveMember(MemberRequest request) {
        return request.toMember()
                .map(value -> memberRepository.save(value).getId()).orElse(null);
    }

    public MemberResponse findMember(String memberId) {
        return MemberResponse.buildResponse(memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다.")));
    }

}

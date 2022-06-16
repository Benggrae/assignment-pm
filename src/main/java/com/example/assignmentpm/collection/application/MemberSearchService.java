package com.example.assignmentpm.collection.application;

import com.example.assignmentpm.collection.domain.Member;
import com.example.assignmentpm.collection.dto.MemberResponse;
import com.example.assignmentpm.collection.infrastructure.dynamoRepository.MemberRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberRepository memberRepository;

    @Cacheable(cacheNames = "member",unless="#result == null", key = "#member.email", cacheManager = "cacheManager")
    public Optional<Member> hasEmailAndHp(Member member) {
        return memberRepository.findByEmailAndHp(member.getEmail(), member.getHp());
    }

    @Cacheable(cacheNames = "findMember",unless="#result == null", key = "#memberId", cacheManager = "cacheManager")
    public MemberResponse findMember(String memberId) {
        return MemberResponse.buildResponse(memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다.")));
    }


}

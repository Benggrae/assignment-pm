package com.example.assignmentpm.collection.application;

import com.example.assignmentpm.collection.domain.Member;
import com.example.assignmentpm.collection.dto.MemberResponse;
import com.example.assignmentpm.collection.infrastructure.dynamoRepository.MemberRepository;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisException;
import java.util.NoSuchElementException;
import java.util.Optional;
import jdk.jfr.Timespan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberSearchService {

    private final MemberRepository memberRepository;

    @Retryable(value = { RedisException.class, QueryTimeoutException.class}
            , maxAttempts = 2
            , recover = "hasEmailAndHpRecover")
    @Cacheable(cacheNames = "member",unless="#result == null", key = "#member.email", cacheManager = "cacheManager")
    public Optional<Member> hasEmailAndHp(Member member) {
        return findHasEmailAndHop(member);
    }

    private Optional<Member> findHasEmailAndHop(Member member) {
        return memberRepository.findByEmailAndHp(member.getEmail(), member.getHp());
    }

    @Recover
    public Optional<Member> hasEmailAndHpRecover(Exception e, Member member) {
        log.error(e.getMessage());
        return findHasEmailAndHop(member);
    }


    @Retryable(value =  { RedisException.class, QueryTimeoutException.class},
            maxAttempts = 2,
            recover = "findNyMemberRecover")

    @Cacheable(cacheNames = "findMember",unless="#result == null", key = "#memberId", cacheManager = "cacheManager")
    public MemberResponse findMember(String memberId) {
        return findNyMemberId(memberId);
    }

    private MemberResponse findNyMemberId(String memberId) {
        return MemberResponse.buildResponse(memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다.")));
    }

    @Recover
    private MemberResponse findNyMemberRecover(Exception e, String memberId) {
        log.error(e.getMessage());
        return findNyMemberId(memberId);
    }

}

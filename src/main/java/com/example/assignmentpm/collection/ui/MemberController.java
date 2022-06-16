package com.example.assignmentpm.collection.ui;

import com.example.assignmentpm.collection.application.MemberSearchService;
import com.example.assignmentpm.collection.application.MemberService;
import com.example.assignmentpm.collection.dto.MemberRequest;
import com.example.assignmentpm.collection.dto.MemberResponse;
import com.example.assignmentpm.collection.infrastructure.dynamoRepository.MemberRepository;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpRequest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/collection")
@EnableCaching
public class MemberController {

    private final MemberService memberService;
    private final MemberSearchService memberSearchService;
    private final MemberRepository memberRepository;
    private final Date NOW_DATE = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

    @PostMapping("member")
    public ResponseEntity<Void> member(@Valid @RequestBody MemberRequest memberRequest, HttpRequest httpRequest) {

        String memberId = memberService.saveMember(memberRequest);
        if (memberId == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.created(URI.create("/api/v1/collection/members/" + memberId)).build();
    }

    @GetMapping("members/{memberId}")
    public ResponseEntity<MemberResponse> memberInfo(@PathVariable(name = "memberId") String memberId) {
        return ResponseEntity.ok(memberSearchService.findMember(memberId));
    }

    @GetMapping("members/today")
    public ResponseEntity<List<MemberResponse>> members() {
        return ResponseEntity.ok(
                memberRepository.findAllByCreateDtAfter(NOW_DATE)
                .stream()
                .map(MemberResponse::buildResponse)
                .collect(Collectors.toList())
        );
    }


}

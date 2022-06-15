package com.example.assignmentpm.collection.ui;

import com.example.assignmentpm.collection.application.MemberSearchService;
import com.example.assignmentpm.collection.application.MemberService;
import com.example.assignmentpm.collection.domain.Member;
import com.example.assignmentpm.collection.dto.MemberRequest;
import com.example.assignmentpm.collection.dto.MemberResponse;
import com.example.assignmentpm.collection.infrastructure.dynamoRepository.MemberRepository;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/collection")
@EnableCaching
public class MemberController {

    private final MemberService memberService;
    private final MemberSearchService memberSearchService;
    private final MemberRepository memberRepository;

    @PostMapping("member")
    public ResponseEntity<Void> member(@Valid @RequestBody MemberRequest memberRequest) {

        String memberId = memberService.saveMember(memberRequest);
        if (memberId == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.created(URI.create("api/v1/collection/members/" + memberId)).build();
    }

    @GetMapping("members/{memberId}")
    public ResponseEntity<MemberResponse> memberInfo(@PathVariable(name = "memberId") String memberId) {
        return ResponseEntity.ok(memberSearchService.findMember(memberId));
    }

    @GetMapping("members")
    public ResponseEntity<List<MemberResponse>> members(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int pageSize) {

        final Page<Member> all = memberRepository.findAll(PageRequest.of(page, pageSize));

        System.out.println("all = " + all);

        return null;
//        all.getTotalElements()
//        return ResponseEntity.ok(memberRepository.findAll(PageRequest.of(page, pageSize))
//                .stream().map(MemberResponse::buildResponse)
//                .collect(Collectors.toList()));
    }


}

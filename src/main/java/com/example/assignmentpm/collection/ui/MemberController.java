package com.example.assignmentpm.collection.ui;

import com.example.assignmentpm.collection.application.MemberSearchService;
import com.example.assignmentpm.collection.application.MemberService;
import com.example.assignmentpm.collection.dto.MemberRequest;
import com.example.assignmentpm.collection.dto.MemberResponse;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("member")
    public ResponseEntity<Void> member(@Valid @RequestBody MemberRequest memberRequest) {

        String memberId = memberService.saveMember(memberRequest);
        if (memberId == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.created(URI.create("/collection/members/" + memberId)).build();
    }

    @GetMapping("members/{memberId}")
    public ResponseEntity<MemberResponse> memberInfo(@PathVariable(name = "memberId") String memberId) {
        return ResponseEntity.ok(memberSearchService.findMember(memberId));
    }
}

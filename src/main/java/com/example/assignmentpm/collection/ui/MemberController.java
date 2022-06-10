package com.example.assignmentpm.collection.ui;

import com.example.assignmentpm.collection.domain.Member;
import com.example.assignmentpm.collection.dto.MemberRequest;
import com.example.assignmentpm.collection.infrastructure.dynamoRepository.MemberRepository;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping("member")
    public ResponseEntity<Void> member(MemberRequest memberRequest) {
        Member save = memberRepository.save(memberRequest.toMember());
        return ResponseEntity.created(URI.create("/collections/members" + save.getId())).build();
    }

    @GetMapping("members")
    public ResponseEntity<List<Member>> member() {
        return ResponseEntity.ok(Streamable.of(memberRepository.findAll()).toList());
    }
}

package com.example.assignmentpm.collection.ui;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.assignmentpm.collection.domain.Member;
import com.example.assignmentpm.collection.dto.MemberRequest;
import com.example.assignmentpm.collection.infrastructure.dynamoRepository.MemberRepository;
import java.net.URI;
import java.util.List;


import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    private final DynamoDBMapper dynamoDBMapper;

    private final AmazonDynamoDB dynamoDB;
    @PostConstruct
    public void setUpTable() {
        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(Member.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
    }


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

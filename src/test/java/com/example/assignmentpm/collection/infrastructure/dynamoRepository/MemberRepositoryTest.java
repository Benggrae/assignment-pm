package com.example.assignmentpm.collection.infrastructure.dynamoRepository;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.assignmentpm.collection.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private AmazonDynamoDB dynamoDB;

    @Autowired
    private DynamoDBMapper dynamoDbMapper;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(Member.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
    }

    @Test
    @DisplayName("Member가 저장된다.")
    void save() {
        //given
        String name = "이름";
        String id = "1";

        //when
        memberRepository.save(Member.builder()
                        .name(name)
                        .id(id).build());

        //then
        Member saveMember = memberRepository.findAll().iterator().next();
        assertThat(saveMember.getId()).isEqualTo(id);
        assertThat(saveMember.getName()).isEqualTo(name);
    }

}

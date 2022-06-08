package com.example.assignmentpm.marketing.infrastructure.dynamoRepository;

import com.example.assignmentpm.marketing.domain.Member;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface MemberRepository extends CrudRepository<Member,String> {

}

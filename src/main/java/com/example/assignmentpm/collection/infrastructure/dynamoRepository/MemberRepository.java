package com.example.assignmentpm.collection.infrastructure.dynamoRepository;

import com.example.assignmentpm.collection.domain.Member;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@EnableScan
public interface MemberRepository extends CrudRepository<Member,String> {

}

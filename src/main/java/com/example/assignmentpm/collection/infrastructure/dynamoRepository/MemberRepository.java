package com.example.assignmentpm.collection.infrastructure.dynamoRepository;

import com.example.assignmentpm.collection.domain.Member;
import java.util.Optional;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


@EnableScan
public interface MemberRepository extends CrudRepository<Member,String> {

    Optional<Member> findByEmailAndHp(String email, String hp);

    Page<Member> findAll(Pageable pageable);
}

package com.example.assignmentpm.collection.infrastructure.dynamoRepository;

import com.example.assignmentpm.collection.domain.Member;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


@EnableScan
public interface MemberRepository extends PagingAndSortingRepository<Member,String> {

    Optional<Member> findByEmailAndHp(String email, String hp);

    @EnableScanCount
    Page<Member> findAll(Pageable pageable);

    List<Member> findAllByCreateDtAfter(Date date);
}

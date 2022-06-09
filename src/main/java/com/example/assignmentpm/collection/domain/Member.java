package com.example.assignmentpm.collection.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "member")
public class Member {
    @Id
    @DynamoDBHashKey
    //다이나모디비는 세터를 통해 주입
    private String id;

    @DynamoDBAttribute
    private String name;

    @Builder
    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

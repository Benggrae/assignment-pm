package com.example.assignmentpm.collection.infrastructure;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableDynamoDBRepositories(basePackages = {"com.example.assignmentpm.collection.infrastructure.dynamoRepository"})
public class AwsDynamoDbConfig {


    @Bean
    @Primary
    public DynamoDBMapperConfig dynamoDbConfig() {
        return DynamoDBMapperConfig.DEFAULT;
    }

    @Bean
    @Primary
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig config) {
        return new DynamoDBMapper(amazonDynamoDB, config);
    }

    @Profile("prod")
    @Bean
    @Primary
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(InstanceProfileCredentialsProvider.getInstance())
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Primary
    @Profile("test")
    @Bean(name = "amazonDynamoDB")
    public AmazonDynamoDB embeddedAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("local", "local")))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://127.0.0.1:8000",
                        Regions.AP_NORTHEAST_2.getName()))
                .build();
    }

    @Primary
    @Profile("local")
    @Bean(name = "amazonDynamoDB")
    public AmazonDynamoDB localAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(new BasicAWSCredentials("awsAccessKey", "awsSecretKey")))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "awsRegion"))
                .build();

    }

}

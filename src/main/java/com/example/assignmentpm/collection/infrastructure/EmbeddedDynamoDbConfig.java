package com.example.assignmentpm.collection.infrastructure;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.example.assignmentpm.collection.infrastructure.util.AwsDynamoDbLocalTestUtils;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Slf4j
@RequiredArgsConstructor
@Configuration
@Profile("local")
//@ConditionalOnProperty(name = "embedded-dynamodb.use", havingValue = "true")
public class EmbeddedDynamoDbConfig {

    private DynamoDBProxyServer proxyServer;

    @PostConstruct
    public void start() {
        if (proxyServer != null) {
            return;
        }

        try {
            AwsDynamoDbLocalTestUtils.initSqLite();
            proxyServer = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory"});
            proxyServer.start();
            log.info("Start Embedded DynamoDB");
        } catch (Exception e) {
            throw new IllegalStateException("Fail Start Embedded DynamoDB", e);
        }
    }

    @PreDestroy
    public void stop() {
        if (proxyServer == null) {
            return;
        }

        try {
            log.info("Stop Embedded DynamoDB");
            proxyServer.stop();
        } catch (Exception e) {
            throw new IllegalStateException("Fail Stop Embedded DynamoDB", e);
        }
    }


}

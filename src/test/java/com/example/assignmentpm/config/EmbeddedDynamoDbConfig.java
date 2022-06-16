package com.example.assignmentpm.config;


import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
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

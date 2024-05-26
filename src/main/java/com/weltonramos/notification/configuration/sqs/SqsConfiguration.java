package com.weltonramos.notification.configuration.sqs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class SqsConfiguration {

    @Value("${AWS_ACCESS_KEY_ID}")
    private String accessKeyId;

    @Value("${AWS_SECRET_ACCESS_KEY}")
    private String secretAccessKey;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {

        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey));

        return SqsAsyncClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.SA_EAST_1)
                .credentialsProvider(staticCredentialsProvider)
                .build();
    }

}

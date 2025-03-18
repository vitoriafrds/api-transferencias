package br.com.app.transferencia.config.sqs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

import static software.amazon.awssdk.regions.Region.SA_EAST_1;

@Configuration
public class SQSConfiguration {

    @Bean
    public static SqsClient getSqsClient() {
        return SqsClient.builder()
                .region(SA_EAST_1)
                .endpointOverride(URI.create("http://localhost:4566"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("dummy_key", "dummy_secret")))
                .build();
    }
}

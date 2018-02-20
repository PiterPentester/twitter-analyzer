package io.github.gandrade.analyzer.twitter;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableBatchProcessing
@EnableNeo4jRepositories
public class TwitterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterServiceApplication.class, args);
    }
}

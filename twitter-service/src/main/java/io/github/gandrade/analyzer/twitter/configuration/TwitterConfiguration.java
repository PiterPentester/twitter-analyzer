package io.github.gandrade.analyzer.twitter.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

@ConfigurationProperties("twitter")
@Component
@Data
public class TwitterConfiguration {

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;


    @Bean
    public TwitterTemplate twitterTemplate(@Value("${twitter.consumer-key}") String consumerKey,
                                           @Value("${twitter.consumer-secret}") String consumerSecret,
                                           @Value("${twitter.access-token}") String accessToken,
                                           @Value("${twitter.access-token-secret}") String accessTokenSecret) {
        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}

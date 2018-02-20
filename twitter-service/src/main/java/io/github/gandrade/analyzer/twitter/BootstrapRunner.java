package io.github.gandrade.analyzer.twitter;

import io.github.gandrade.analyzer.twitter.service.TwitterSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.social.RateLimitExceededException;
import org.springframework.stereotype.Component;


// @Component
@Slf4j
public class BootstrapRunner implements CommandLineRunner {

    private TwitterSearchService twitterService;

    @Autowired
    public BootstrapRunner(TwitterSearchService twitterService) {
        this.twitterService = twitterService;
    }

    @Override
    public void run(String... args) {
        try {
            twitterService.removeAll();
            twitterService.fetchFollowers();
            twitterService.fetchFollowing();
        } catch (RateLimitExceededException e) {
            log.error("Unable to fetchFollowers Twitter's data, try again later.");
        }

    }


}

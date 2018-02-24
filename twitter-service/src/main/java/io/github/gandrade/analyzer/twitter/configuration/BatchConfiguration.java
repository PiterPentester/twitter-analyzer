package io.github.gandrade.analyzer.twitter.configuration;

import io.github.gandrade.analyzer.twitter.batch.TwitterProfileProcessor;
import io.github.gandrade.analyzer.twitter.domain.Profile;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class BatchConfiguration {

    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;
    private TwitterTemplate twitterTemplate;

    @Autowired
    public BatchConfiguration(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory, TwitterTemplate twitterTemplate) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.twitterTemplate = twitterTemplate;
    }

    @Bean
    public ItemProcessor twitterProcessor() {
        return new TwitterProfileProcessor();
    }

    @Bean
    public ItemReader<TwitterProfile> twitterFollowersReader() {
        CursoredList<TwitterProfile> followers = twitterTemplate.friendOperations().getFollowers();
        return new TwitterFollowersReader<>(followers);
//        return new IteratorItemReader<>(twitterTemplate
//                .friendOperations()
//                .getFollowers()
//                .iterator());
    }

    @Bean
    public Step importTwitterFollowers() {
        return stepBuilderFactory.get("Importing Twitter Followers")
                .<TwitterProfile, Profile>chunk(10_000)
                .reader(this.twitterFollowersReader())
                .processor(this.twitterProcessor())
                .build();
    }

    @Bean
    public ItemReader<TwitterProfile> twitterFriendsReader() {
        return new IteratorItemReader<>(twitterTemplate
                .friendOperations()
                .getFriends()
                .iterator());
    }

    @Bean
    public Step importTwitterFriends() {
        return stepBuilderFactory.get("Importing Twitter Friends")
                .<TwitterProfile, Profile>chunk(10_000)
                .reader(this.twitterFriendsReader())
                .processor(this.twitterProcessor())
                .build();
    }

    @Bean
    public Job twitterFlow() {
        return jobBuilderFactory.get("twitter-job")
                .incrementer(new RunIdIncrementer())
                .start(this.importTwitterFollowers())
                .next(importTwitterFriends()).build();

    }

}

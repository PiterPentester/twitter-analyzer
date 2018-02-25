package io.github.gandrade.analyzer.twitter.batch;

import io.github.gandrade.analyzer.twitter.domain.Profile;
import io.github.gandrade.analyzer.twitter.mapping.TwitterProfileMapper;
import io.github.gandrade.analyzer.twitter.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.HashSet;
import java.util.List;

@Slf4j
public class TwitterFollowersWriter implements ItemWriter<Profile> {

    @Autowired
    private ProfileRepository repository;
    @Autowired
    private TwitterProfileMapper mapper;
    @Autowired
    private TwitterTemplate twitterTemplate;
    private Profile ownProfile;


    @Override
    public void write(List<? extends Profile> items) {
        log.info("Saving profile items collected {}", items.size());
        repository.saveAll(items);

        this.ownProfile = mapper.twitterProfileToProfile(getTwitterProfile());
        HashSet<Profile> followers = new HashSet<>(items);
        ownProfile.setFollowers(followers);

        repository.save(ownProfile);
    }

    private TwitterProfile getTwitterProfile() {
        log.info("Getting own user profile");
        return twitterTemplate.userOperations().getUserProfile();
    }

}

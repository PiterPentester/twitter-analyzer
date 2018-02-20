package io.github.gandrade.analyzer.twitter.batch;

import io.github.gandrade.analyzer.twitter.domain.Profile;
import io.github.gandrade.analyzer.twitter.mapping.TwitterProfileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TwitterProfile;

@Slf4j
public class TwitterProfileProcessor implements ItemProcessor<TwitterProfile, Profile> {

    @Autowired
    private TwitterProfileMapper twitterProfileMapper;

    @Override
    public Profile process(TwitterProfile item) {
        Profile profile = twitterProfileMapper.twitterProfileToProfile(item);
        log.info(String.format("Handling profile %s", profile.toString()));
        return profile;
    }
}

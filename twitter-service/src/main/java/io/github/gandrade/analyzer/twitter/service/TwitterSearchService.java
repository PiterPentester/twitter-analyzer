package io.github.gandrade.analyzer.twitter.service;

import io.github.gandrade.analyzer.twitter.domain.Profile;
import io.github.gandrade.analyzer.twitter.mapping.TwitterProfileMapper;
import io.github.gandrade.analyzer.twitter.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TwitterSearchService {

    private TwitterTemplate twitterTemplate;
    private TwitterProfileMapper twitterProfileMapper;
    private ProfileRepository repository;
    private Profile ownerProfile;

    @Autowired
    public TwitterSearchService(TwitterTemplate twitterTemplate,
                                TwitterProfileMapper twitterProfileMapper,
                                ProfileRepository repository) {
        this.twitterTemplate = twitterTemplate;
        this.twitterProfileMapper = twitterProfileMapper;
        this.repository = repository;
        this.ownerProfile = twitterProfileMapper.twitterProfileToProfile(twitterTemplate.userOperations().getUserProfile());
    }

    public void removeAll() {
        repository.deleteAll();
    }

    public void fetchFollowing() {
        List<Profile> profiles = Collections.EMPTY_LIST;
        log.info("Searching for gustavo's followers");
        CursoredList<TwitterProfile> twitterCursor = twitterTemplate
                .friendOperations()
                .getFriends();
        Set<Profile> friends = cursor(twitterCursor);
        ownerProfile.setFriends(friends);
        repository.save(ownerProfile);
    }


    public void fetchFollowers() {
        // Profile profile = twitterProfileMapper.twitterProfileToProfile(new TwitterProfile(1l, "screenName", "name", "url", "profileImageUrl", "description", "location", null));
        List<Profile> profiles = Collections.EMPTY_LIST;
        log.info("Searching for gustavo's followers");
        CursoredList<TwitterProfile> twitterCursor = twitterTemplate
                .friendOperations()
                .getFollowers();

        Set<Profile> followers = cursor(twitterCursor);
        ownerProfile.setFollowers(followers);
        repository.save(ownerProfile);

        log.info(String.format("Finished twitter search %d elements %s", profiles.size(), profiles));
    }

    private Set<Profile> cursor(CursoredList<TwitterProfile> twitterCursor) {
        while (twitterCursor.hasNext()) {
            log.info("----------------------------------");
            Set<Profile> followers = twitterCursor
                    .stream()
                    .map(this::acl)
                    .map(repository::save)
                    .collect(Collectors.toSet());

            return followers;
/*            twitterCursor = twitterTemplate
                    .friendOperations()
                    .getFollowersInCursor(twitterCursor.getNextCursor());*/
        }
        return Collections.EMPTY_SET;
    }

    private Profile acl(TwitterProfile twitterProfile) {
        Profile profile = twitterProfileMapper.twitterProfileToProfile(twitterProfile);
        log.info(String.format("Handling profile %s", profile.toString()));
        return profile;
    }
}

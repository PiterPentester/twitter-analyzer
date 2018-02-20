package io.github.gandrade.analyzer.twitter.mapping;

import io.github.gandrade.analyzer.twitter.domain.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.social.twitter.api.TwitterProfile;

@Mapper
public interface TwitterProfileMapper {

    @Mappings({
            @Mapping(source = "following", target = "followsMeBack"),
            @Mapping(source = "id", target = "twitterId"),
            @Mapping(target = "id", ignore = true)
    })
    Profile twitterProfileToProfile(TwitterProfile profile);
}

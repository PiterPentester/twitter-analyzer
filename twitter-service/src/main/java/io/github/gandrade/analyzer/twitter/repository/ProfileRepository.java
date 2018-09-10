package io.github.gandrade.analyzer.twitter.repository;

import io.github.gandrade.analyzer.twitter.domain.Profile;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, Long> {

    Optional<Profile> findByScreenName(String screenName);
    Optional<Profile> findByName(String name);

    boolean existsByScreenName(String screenName);
    boolean existsByName(String name);
}

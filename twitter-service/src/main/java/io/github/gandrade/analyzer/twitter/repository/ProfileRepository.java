package io.github.gandrade.analyzer.twitter.repository;

import io.github.gandrade.analyzer.twitter.domain.Profile;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, Long> {

}

package io.github.gandrade.analyzer.twitter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;


@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Profile {

    @GraphId
    private Long id;
    private Long twitterId;
    private String screenName;
    private String name;
    private String url;
    private String profileImageUrl;
    private String description;
    private String location;
    // private Date createdDate;
    private String language;

    @Relationship(type = "FOLLOWERS", direction = INCOMING)
    private Set<Profile> followers;

    @Relationship(type = "FOLLOWING")
    private Set<Profile> friends;

    private boolean followsMeBack;

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", screenName='" + screenName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

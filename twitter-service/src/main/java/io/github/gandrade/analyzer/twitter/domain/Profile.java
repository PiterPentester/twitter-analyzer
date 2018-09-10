package io.github.gandrade.analyzer.twitter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;
import static org.neo4j.ogm.annotation.Relationship.OUTGOING;


@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Profile {

    @Id
    @GeneratedValue
    private Long id;

    @Index
    private Long twitterId;

    @Index
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

    @Relationship(type = "FOLLOWING", direction = OUTGOING)
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

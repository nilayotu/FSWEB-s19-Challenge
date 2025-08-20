package com.workintech.twitter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "retweet", schema = "twitter-project")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Retweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != getClass())
            return false;

        Retweet retweet = (Retweet) obj;

        return id != null && id.equals(retweet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

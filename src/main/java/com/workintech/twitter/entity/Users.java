package com.workintech.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users", schema = "twitter-project")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50)
    private String username;

    @Email(message = "Invalid email format")
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 100)
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ---- Relationships ----
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tweet> tweets = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Retweet> retweets = new ArrayList<>();

    // ---- Helper Methods ----
    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
        tweet.setUsers(this);
    }

    public void removeTweet(Tweet tweet) {
        tweets.remove(tweet);
        tweet.setUsers(null);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setUsers(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setUsers(null);
    }

    public void addLike(Like like) {
        likes.add(like);
        like.setUsers(this);
    }

    public void removeLike(Like like) {
        likes.remove(like);
        like.setUsers(null);
    }

    public void addRetweet(Retweet retweet) {
        retweets.add(retweet);
        retweet.setUsers(this);
    }

    public void removeRetweet(Retweet retweet) {
        retweets.remove(retweet);
        retweet.setUsers(null);
    }

    // Read-only getters
    public List<Tweet> getTweets() {
        return Collections.unmodifiableList(this.tweets);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(this.comments);
    }

    public List<Like> getLikes() {
        return Collections.unmodifiableList(this.likes);
    }

    public List<Retweet> getRetweets() {
        return Collections.unmodifiableList(this.retweets);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        Users users = (Users) obj;
        return id != null && id.equals(users.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}

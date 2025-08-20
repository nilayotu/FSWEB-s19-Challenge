package com.workintech.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "tweet", schema = "twitter-project")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    @NotNull(message = "Content cannot be null")
    @NotEmpty(message = "Content cannot be empty")
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 280)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users users;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Retweet> retweets = new ArrayList<>();

    // ---- Helper Methods ----
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setTweet(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setTweet(null);
    }

    public void addLike(Like like) {
        likes.add(like);
        like.setTweet(this);
    }

    public void removeLike(Like like) {
        likes.remove(like);
        like.setTweet(null);
    }

    public void addRetweet(Retweet retweet) {
        retweets.add(retweet);
        retweet.setTweet(this);
    }

    public void removeRetweet(Retweet retweet) {
        retweets.remove(retweet);
        retweet.setTweet(null);
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
        Tweet tweet = (Tweet) obj;
        return id != null && id.equals(tweet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


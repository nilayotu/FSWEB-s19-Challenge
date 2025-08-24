package com.workintech.twitter.service;

import com.workintech.twitter.dto.RetweetRequestDto;
import com.workintech.twitter.dto.RetweetResponseDto;
import com.workintech.twitter.dto.TweetResponseDto;
import com.workintech.twitter.dto.UsersResponseDto;
import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.Users;
import com.workintech.twitter.repository.RetweetRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RetweetServiceImpl implements RetweetService{

    private final RetweetRepository retweetRepository;
    private final UsersRepository usersRepository;
    private final TweetRepository tweetRepository;

    @Override
    public RetweetResponseDto retweet(RetweetRequestDto requestDto) {
        Users user = usersRepository.findById(requestDto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Tweet tweet = tweetRepository.findById(requestDto.tweetId())
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        // Check if the user already retweeted this tweet
        Optional<Retweet> existingRetweet = retweetRepository.findAll().stream()
                .filter(r -> r.getUsers().getId().equals(user.getId()) &&
                        r.getTweet().getId().equals(tweet.getId()))
                .findFirst();

        if (existingRetweet.isPresent()) {
            throw new RuntimeException("User has already retweeted this tweet");
        }

        Retweet retweet = new Retweet();
        retweet.setUsers(user);
        retweet.setTweet(tweet);

        Retweet saved = retweetRepository.save(retweet);

        return mapToResponseDto(saved);
    }

    @Override
    public void delete(Long retweetId, Long userId) {
        Retweet retweet = retweetRepository.findById(retweetId)
                .orElseThrow(() -> new RuntimeException("Retweet not found"));

        if (!retweet.getUsers().getId().equals(userId)) {
            throw new RuntimeException("You can only delete your own retweet!");
        }

        retweetRepository.delete(retweet);
    }

    // Utility method to map Retweet -> RetweetResponseDto
    private RetweetResponseDto mapToResponseDto(Retweet retweet) {
        UsersResponseDto userDto = new UsersResponseDto(
                retweet.getUsers().getId(),
                retweet.getUsers().getUsername(),
                retweet.getUsers().getEmail()
        );

        TweetResponseDto tweetDto = new TweetResponseDto(
                retweet.getTweet().getId(),
                retweet.getTweet().getContent(),
                userDto // optional: original tweet's author info
        );

        return new RetweetResponseDto(
                retweet.getId(),
                userDto,
                tweetDto
        );
    }
}

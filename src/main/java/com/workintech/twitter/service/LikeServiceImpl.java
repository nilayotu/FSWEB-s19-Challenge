package com.workintech.twitter.service;

import com.workintech.twitter.dto.LikeRequestDto;
import com.workintech.twitter.dto.LikeResponseDto;
import com.workintech.twitter.dto.TweetResponseDto;
import com.workintech.twitter.dto.UsersResponseDto;
import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.Users;
import com.workintech.twitter.exception.AlreadyExistsException;
import com.workintech.twitter.exception.LikeNotFoundException;
import com.workintech.twitter.exception.TweetNotFoundException;
import com.workintech.twitter.exception.UserNotFoundException;
import com.workintech.twitter.repository.LikeRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final UsersRepository usersRepository;
    private final TweetRepository tweetRepository;

    @Override
    public LikeResponseDto like(LikeRequestDto requestDto) {
        Users user = usersRepository.findById(requestDto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + requestDto.userId()));
        Tweet tweet = tweetRepository.findById(requestDto.tweetId())
                .orElseThrow(() -> new TweetNotFoundException("Tweet not found with id: " + requestDto.tweetId()));

        Optional<Like> existingLike = likeRepository.findAll().stream()
                .filter(l -> l.getUsers().getId().equals(user.getId()) &&
                        l.getTweet().getId().equals(tweet.getId()))
                .findFirst();

        if (existingLike.isPresent()) {
            throw new AlreadyExistsException("User already liked this tweet");
        }

        Like like = new Like();
        like.setUsers(user);
        like.setTweet(tweet);

        Like saved = likeRepository.save(like);

        return mapToResponseDto(saved);
    }

    @Override
    public void dislike(Long userId, Long tweetId) {
        Optional<Like> existingLike = likeRepository.findAll().stream()
                .filter(l -> l.getUsers().getId().equals(userId) &&
                        l.getTweet().getId().equals(tweetId))
                .findFirst();

        if (existingLike.isEmpty()) {
            throw new LikeNotFoundException("Like not found for userId: " + userId + " and tweetId: " + tweetId);
        }

        likeRepository.delete(existingLike.get());
    }

    private LikeResponseDto mapToResponseDto(Like like) {
        UsersResponseDto userDto = new UsersResponseDto(
                like.getUsers().getId(),
                like.getUsers().getUsername(),
                like.getUsers().getEmail()
        );

        TweetResponseDto tweetDto = new TweetResponseDto(
                like.getTweet().getId(),
                like.getTweet().getContent(),
                userDto
        );

        return new LikeResponseDto(
                like.getId(),
                userDto,
                tweetDto
        );
    }
}

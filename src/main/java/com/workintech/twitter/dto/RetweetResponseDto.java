package com.workintech.twitter.dto;

public record RetweetResponseDto(
        Long id,
        UsersResponseDto user,
        TweetResponseDto originalTweet
) {
}

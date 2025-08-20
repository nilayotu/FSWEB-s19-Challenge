package com.workintech.twitter.dto;

public record LikeResponseDto(
        Long id,
        UsersResponseDto user,
        TweetResponseDto tweet
) {
}

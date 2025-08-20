package com.workintech.twitter.dto;

public record TweetResponseDto(
        Long id,
        String content,
        UsersResponseDto user
) {
}

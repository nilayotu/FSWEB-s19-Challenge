package com.workintech.twitter.dto;

public record TweetRequestDto(
        String content,
        Long userId
) {
}

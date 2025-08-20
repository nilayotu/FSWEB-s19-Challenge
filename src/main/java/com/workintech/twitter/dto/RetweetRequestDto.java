package com.workintech.twitter.dto;

public record RetweetRequestDto(
        Long userId,
        Long tweetId
) {
}

package com.workintech.twitter.dto;

public record LikeRequestDto(
        Long userId,
        Long tweetId
) {
}

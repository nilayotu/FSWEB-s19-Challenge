package com.workintech.twitter.dto;

public record CommentRequestDto(
        String content,
        Long userId,
        Long tweetId
) {
}

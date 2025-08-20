package com.workintech.twitter.dto;

public record CommentResponseDto(
        Long id,
        String content,
        UsersResponseDto user,
        TweetResponseDto tweet
) {
}

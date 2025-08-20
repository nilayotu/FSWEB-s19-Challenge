package com.workintech.twitter.service;

import com.workintech.twitter.dto.LikeRequestDto;
import com.workintech.twitter.dto.LikeResponseDto;

public interface LikeService {
    LikeResponseDto like(LikeRequestDto requestDto);
    void dislike(Long userId, Long tweetId);
}

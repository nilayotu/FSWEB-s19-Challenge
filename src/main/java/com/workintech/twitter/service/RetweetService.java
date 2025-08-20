package com.workintech.twitter.service;

import com.workintech.twitter.dto.RetweetRequestDto;
import com.workintech.twitter.dto.RetweetResponseDto;

public interface RetweetService {
    RetweetResponseDto retweet(RetweetRequestDto requestDto);
    void delete(Long retweetId, Long userId);
}

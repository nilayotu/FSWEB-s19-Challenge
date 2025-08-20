package com.workintech.twitter.service;

import com.workintech.twitter.dto.TweetRequestDto;
import com.workintech.twitter.dto.TweetResponseDto;
import com.workintech.twitter.dto.TweetUpdateDto;

import java.util.List;

public interface TweetService {
    TweetResponseDto create(TweetRequestDto requestDto);
    TweetResponseDto getById(Long id);
    List<TweetResponseDto> getByUserId(Long userId);
    TweetResponseDto update(Long id, TweetUpdateDto updateDto);
    void delete(Long id, Long userId);
}

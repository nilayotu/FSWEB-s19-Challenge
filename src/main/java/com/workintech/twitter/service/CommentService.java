package com.workintech.twitter.service;

import com.workintech.twitter.dto.CommentRequestDto;
import com.workintech.twitter.dto.CommentResponseDto;
import com.workintech.twitter.dto.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto create(CommentRequestDto requestDto);
    CommentResponseDto getById(Long id);
    List<CommentResponseDto> getByTweetId(Long tweetId);
    CommentResponseDto update(Long id, CommentUpdateDto updateDto, Long userId);
    void delete(Long id, Long userId);
}

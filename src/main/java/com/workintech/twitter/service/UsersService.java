package com.workintech.twitter.service;

import com.workintech.twitter.dto.UsersRequestDto;
import com.workintech.twitter.dto.UsersResponseDto;
import com.workintech.twitter.dto.UsersUpdateDto;

import java.util.List;

public interface UsersService {
    UsersResponseDto create(UsersRequestDto requestDto);
    UsersResponseDto getById(Long id);
    List<UsersResponseDto> getAll();
    UsersResponseDto update(Long id, UsersUpdateDto updateDto);
    void delete(Long id);
}

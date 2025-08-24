package com.workintech.twitter.service;

import com.workintech.twitter.dto.UsersRequestDto;
import com.workintech.twitter.dto.UsersResponseDto;
import com.workintech.twitter.dto.UsersUpdateDto;

import java.util.List;

public interface UsersService {
    UsersResponseDto createUser(UsersRequestDto requestDto);
    UsersResponseDto getUserById(Long id);
    List<UsersResponseDto> getAllUsers();
    UsersResponseDto updateUser(Long id, UsersUpdateDto updateDto);
    void deleteUser(Long id);
}

package com.workintech.twitter.dto;

public record UsersRequestDto(
        String username,
        String email,
        String password
) {
}

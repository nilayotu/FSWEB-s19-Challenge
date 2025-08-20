package com.workintech.twitter.dto;

public record UsersUpdateDto(
        String username,
        String email,
        String password
) {
}

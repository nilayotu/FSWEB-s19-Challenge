package com.workintech.twitter.controller;

import com.workintech.twitter.dto.UsersRequestDto;
import com.workintech.twitter.dto.UsersResponseDto;
import com.workintech.twitter.dto.UsersUpdateDto;
import com.workintech.twitter.service.UsersService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public List<UsersResponseDto> getAll() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UsersResponseDto get(@Positive @PathVariable("id") Long id) {
        return usersService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public UsersResponseDto create(@Valid @RequestBody UsersRequestDto requestDto) {
        return usersService.createUser(requestDto);
    }

    @PutMapping("/{id}")
    public UsersResponseDto replaceOrCreate(@Positive @PathVariable("id") Long id,
                                            @Valid @RequestBody UsersUpdateDto updateDto) {
        return usersService.updateUser(id, updateDto);
    }

    @PatchMapping("/{id}")
    public UsersResponseDto update(@Positive @PathVariable("id") Long id,
                                   @Valid @RequestBody UsersUpdateDto updateDto) {
        return usersService.updateUser(id, updateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@Positive @PathVariable("id") Long id) {
        usersService.deleteUser(id);
    }
}
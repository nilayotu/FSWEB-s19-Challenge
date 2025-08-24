package com.workintech.twitter.controller;

import com.workintech.twitter.dto.TweetRequestDto;
import com.workintech.twitter.dto.TweetResponseDto;
import com.workintech.twitter.dto.TweetUpdateDto;
import com.workintech.twitter.service.TweetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
@Validated
public class TweetController {

    private final TweetService tweetService;

    // --- GET ALL TWEETS BY USER ---
    @GetMapping("/user/{userId}")
    public List<TweetResponseDto> getByUserId(@Positive @PathVariable Long userId) {
        return tweetService.getByUserId(userId);
    }

    // --- GET SINGLE TWEET ---
    @GetMapping("/{id}")
    public TweetResponseDto getById(@Positive @PathVariable Long id) {
        return tweetService.getById(id);
    }

    // --- CREATE TWEET ---
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto create(@Valid @RequestBody TweetRequestDto requestDto) {
        return tweetService.create(requestDto);
    }

    // --- UPDATE TWEET (PUT) ---
    @PutMapping("/{id}")
    public TweetResponseDto replace(@Positive @PathVariable Long id,
                                    @Valid @RequestBody TweetUpdateDto updateDto) {
        return tweetService.update(id, updateDto);
    }

    // --- UPDATE TWEET (PATCH) ---
    @PatchMapping("/{id}")
    public TweetResponseDto update(@Positive @PathVariable Long id,
                                   @Valid @RequestBody TweetUpdateDto updateDto) {
        return tweetService.update(id, updateDto);
    }

    // --- DELETE TWEET (only owner can delete) ---
    @DeleteMapping("/{id}/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long id,
                       @Positive @PathVariable Long userId) {
        tweetService.delete(id, userId);
    }
}
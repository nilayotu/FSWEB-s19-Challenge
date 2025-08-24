package com.workintech.twitter.controller;

import com.workintech.twitter.dto.RetweetRequestDto;
import com.workintech.twitter.dto.RetweetResponseDto;
import com.workintech.twitter.service.RetweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweets")
@RequiredArgsConstructor
public class RetweetController {

    private final RetweetService retweetService;

    // ✅ Retweet atma
    @PostMapping
    public ResponseEntity<RetweetResponseDto> retweet(@RequestBody RetweetRequestDto requestDto) {
        RetweetResponseDto response = retweetService.retweet(requestDto);
        return ResponseEntity.ok(response);
    }

    // ✅ Retweet silme
    @DeleteMapping("/{retweetId}/user/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long retweetId, @PathVariable Long userId) {
        retweetService.delete(retweetId, userId);
        return ResponseEntity.ok("Retweet deleted successfully");
    }
}
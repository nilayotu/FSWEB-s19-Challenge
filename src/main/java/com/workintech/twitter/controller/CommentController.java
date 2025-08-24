package com.workintech.twitter.controller;

import com.workintech.twitter.dto.CommentRequestDto;
import com.workintech.twitter.dto.CommentResponseDto;
import com.workintech.twitter.dto.CommentUpdateDto;
import com.workintech.twitter.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 1. Yorum oluştur
    @PostMapping
    public CommentResponseDto create(@Valid @RequestBody CommentRequestDto requestDto) {
        return commentService.create(requestDto);
    }

    // 2. Yorum detayını getir
    @GetMapping("/{id}")
    public CommentResponseDto getById(@Positive @PathVariable Long id) {
        return commentService.getById(id);
    }

    // 3. Bir tweete ait tüm yorumları getir
    @GetMapping("/tweet/{tweetId}")
    public List<CommentResponseDto> getByTweetId(@Positive @PathVariable Long tweetId) {
        return commentService.getByTweetId(tweetId);
    }

    // 4. Yorumu güncelle (sadece sahibi)
    @PutMapping("/{id}")
    public CommentResponseDto update(
            @Positive @PathVariable Long id,
            @Valid @RequestBody CommentUpdateDto updateDto,
            @RequestParam Long userId
    ) {
        return commentService.update(id, updateDto, userId);
    }

    // 5. Yorumu sil (sadece sahibi)
    @DeleteMapping("/{id}")
    public void delete(
            @Positive @PathVariable Long id,
            @RequestParam Long userId
    ) {
        commentService.delete(id, userId);
    }
}
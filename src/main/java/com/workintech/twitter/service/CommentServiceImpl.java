package com.workintech.twitter.service;

import com.workintech.twitter.dto.*;
import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.Users;
import com.workintech.twitter.exception.CommentNotFoundException;
import com.workintech.twitter.exception.TweetNotFoundException;
import com.workintech.twitter.exception.UnauthorizedActionException;
import com.workintech.twitter.exception.UserNotFoundException;
import com.workintech.twitter.repository.CommentRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final TweetRepository tweetRepository;

    @Override
    public CommentResponseDto create(CommentRequestDto requestDto) {
        Users user = usersRepository.findById(requestDto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + requestDto.userId()));
        Tweet tweet = tweetRepository.findById(requestDto.tweetId())
                .orElseThrow(() -> new TweetNotFoundException("Tweet not found with id: " + requestDto.tweetId()));

        Comment comment = new Comment();
        comment.setContent(requestDto.content());
        comment.setUsers(user);
        comment.setTweet(tweet);
        comment.setCreatedAt(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);

        return mapToResponseDto(saved);
    }

    @Override
    public CommentResponseDto getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
        return mapToResponseDto(comment);
    }

    @Override
    public List<CommentResponseDto> getByTweetId(Long tweetId) {
        List<Comment> comments = commentRepository.findAll()
                .stream()
                .filter(c -> c.getTweet().getId().equals(tweetId))
                .collect(Collectors.toList());

        return comments.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto update(Long id, CommentUpdateDto updateDto, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));

        if (!comment.getUsers().getId().equals(userId)) {
            throw new UnauthorizedActionException("You can only update your own comment!");
        }

        comment.setContent(updateDto.content());
        Comment updated = commentRepository.save(comment);

        return mapToResponseDto(updated);
    }

    @Override
    public void delete(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));

        if (!comment.getUsers().getId().equals(userId)) {
            throw new UnauthorizedActionException("You can only delete your own comment!");
        }

        commentRepository.delete(comment);
    }

    private CommentResponseDto mapToResponseDto(Comment comment) {
        UsersResponseDto userDto = new UsersResponseDto(
                comment.getUsers().getId(),
                comment.getUsers().getUsername(),
                comment.getUsers().getEmail()
        );

        TweetResponseDto tweetDto = new TweetResponseDto(
                comment.getTweet().getId(),
                comment.getTweet().getContent(),
                userDto
        );

        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                userDto,
                tweetDto
        );
    }
}
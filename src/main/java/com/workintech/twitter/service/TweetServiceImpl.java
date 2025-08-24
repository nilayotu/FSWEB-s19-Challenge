    package com.workintech.twitter.service;

    import com.workintech.twitter.dto.TweetRequestDto;
    import com.workintech.twitter.dto.TweetResponseDto;
    import com.workintech.twitter.dto.TweetUpdateDto;
    import com.workintech.twitter.dto.UsersResponseDto;
    import com.workintech.twitter.entity.Tweet;
    import com.workintech.twitter.entity.Users;
    import com.workintech.twitter.repository.TweetRepository;
    import com.workintech.twitter.repository.UsersRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class TweetServiceImpl implements TweetService{

        private final TweetRepository tweetRepository;
        private final UsersRepository usersRepository;



        @Override
        public TweetResponseDto create(TweetRequestDto requestDto) {
            Users user = usersRepository.findById(requestDto.userId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Tweet tweet = new Tweet();
            tweet.setContent(requestDto.content());
            tweet.setUsers(user);

            Tweet saved = tweetRepository.save(tweet);

            return new TweetResponseDto(
                    saved.getId(),
                    saved.getContent(),
                    new UsersResponseDto(user.getId(), user.getUsername(), user.getEmail())
            );
        }

        @Override
        public TweetResponseDto getById(Long id) {
            Tweet tweet = tweetRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tweet not found"));

            Users user = tweet.getUsers();
            return new TweetResponseDto(
                    tweet.getId(),
                    tweet.getContent(),
                    new UsersResponseDto(user.getId(), user.getUsername(), user.getEmail())
            );
        }

        @Override
        public List<TweetResponseDto> getByUserId(Long userId) {
            return tweetRepository.findAll().stream()
                    .filter(tweet -> tweet.getUsers().getId().equals(userId))
                    .map(tweet -> new TweetResponseDto(
                            tweet.getId(),
                            tweet.getContent(),
                            new UsersResponseDto(
                                    tweet.getUsers().getId(),
                                    tweet.getUsers().getUsername(),
                                    tweet.getUsers().getEmail()
                            )
                    ))
                    .collect(Collectors.toList());
        }

        @Override
        public TweetResponseDto update(Long id, TweetUpdateDto updateDto) {
            Tweet tweet = tweetRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tweet not found"));

            tweet.setContent(updateDto.content());
            Tweet updated = tweetRepository.save(tweet);

            Users user = updated.getUsers();
            return new TweetResponseDto(
                    updated.getId(),
                    updated.getContent(),
                    new UsersResponseDto(user.getId(), user.getUsername(), user.getEmail())
            );
        }

        @Override
        public void delete(Long id, Long userId) {
            Tweet tweet = tweetRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tweet not found"));

            if (!tweet.getUsers().getId().equals(userId)) {
                throw new RuntimeException("You can delete only your own tweets!");
            }
            tweetRepository.delete(tweet);
        }
    }
package com.workintech.twitter.service;

import com.workintech.twitter.dto.UsersRequestDto;
import com.workintech.twitter.dto.UsersResponseDto;
import com.workintech.twitter.dto.UsersUpdateDto;
import com.workintech.twitter.entity.Users;
import com.workintech.twitter.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;

    @Override
    public UsersResponseDto createUser(UsersRequestDto requestDto) {
        Users user = new Users();
        user.setUsername(requestDto.username());
        user.setEmail(requestDto.email());
        user.setPassword(requestDto.password());

        Users saved = usersRepository.save(user);
        return new UsersResponseDto(saved.getId(), saved.getUsername(), saved.getEmail());
    }

    @Override
    public UsersResponseDto getUserById(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return new UsersResponseDto(user.getId(), user.getUsername(), user.getEmail());
    }

    @Override
    public List<UsersResponseDto> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(u -> new UsersResponseDto(u.getId(), u.getUsername(), u.getEmail()))
                .toList();
    }

    @Override
    public UsersResponseDto updateUser(Long id, UsersUpdateDto updateDto) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (updateDto.username() != null) user.setUsername(updateDto.username());
        if (updateDto.email() != null)    user.setEmail(updateDto.email());
        if (updateDto.password() != null) user.setPassword(updateDto.password());

        Users updated = usersRepository.save(user);
        return new UsersResponseDto(updated.getId(), updated.getUsername(), updated.getEmail());
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
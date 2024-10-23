package com.espeshop.users.services;

import com.espeshop.users.dao.repositories.UserRepository;
import com.espeshop.users.model.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    
    public User createUser(User userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .build();
        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        Page<User> users;
            users = userRepository.findAll(pageable);
        return users.map(this::mapToUserResponse);
    }

    private User mapToUserResponse(User user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}

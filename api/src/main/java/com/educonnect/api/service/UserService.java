package com.educonnect.api.service;

import java.util.Optional;
import java.util.UUID;
import com.educonnect.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.educonnect.api.dto.UserDto;
import com.educonnect.api.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDto> getUserById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = new UserDto(user.getId().toString(), user.getEmail(), user.getRole());
            return Optional.of(userDto);
        }
        return Optional.empty();
    }
}

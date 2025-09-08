package com.educonnect.auth_service.service;

import java.util.Optional;
import java.util.UUID;
import com.educonnect.auth_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.educonnect.auth_service.dto.UserDto;
import com.educonnect.auth_service.model.User;

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
            UserDto userDto = new UserDto();
            userDto.setId(user.getId().toString());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            userDto.setStatus(user.getStatus());
            return Optional.of(userDto);
        }
        return Optional.empty();
    }
}

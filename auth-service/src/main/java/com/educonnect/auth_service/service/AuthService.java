package com.educonnect.auth_service.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.educonnect.auth_service.dto.LoginReqDto;
import com.educonnect.auth_service.dto.RegRequestDto;
import com.educonnect.auth_service.dto.UserDto;
import com.educonnect.auth_service.model.User;
import com.educonnect.auth_service.repository.UserRepository;
import com.educonnect.auth_service.util.JwtUtil;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void registerUser(RegRequestDto regRequestDto) {
        String email = regRequestDto.getEmail();
        String password = regRequestDto.getPassword();
        String role = regRequestDto.getRole();

        if (!role.equals("STUDENT") && !role.equals("ADMIN") && !role.equals("HOME_OWNER")
                && !role.equals("MODERATOR")) {
            throw new RuntimeException("Invalid role");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);
        userRepository.save(newUser);
    }

    public String loginUser(LoginReqDto loginReqDto) {
        String email = loginReqDto.getEmail();
        String password = loginReqDto.getPassword();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UUID id = user.getId();
            email = user.getEmail();
            String role = user.getRole();

            if (passwordEncoder.matches(password, user.getPassword())) {
                // Generate and return a JWT token
                return jwtUtil.generateToken(id, email, role);
            }
        }
        throw new RuntimeException("Invalid email or password");
    }

    public UserDto getUserfromToken(String authToken) {
        String email = jwtUtil.getEmailFromToken(authToken);
        String role = jwtUtil.getRoleFromToken(authToken);
        String id = jwtUtil.getIdFromToken(authToken).toString();
        UserDto userDto = new UserDto(id, email, role);
        return userDto;
    }
}

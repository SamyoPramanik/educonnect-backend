package com.educonnect.auth_service.controller;

import com.educonnect.auth_service.service.AuthService;
import com.educonnect.auth_service.util.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.auth_service.dto.LoginReqDto;
import com.educonnect.auth_service.dto.LoginResDto;
import com.educonnect.auth_service.dto.RegRequestDto;
import com.educonnect.auth_service.dto.UserDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegRequestDto regRequestDto) {
        authService.registerUser(regRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto loginReqDto) {
        String token = authService.loginUser(loginReqDto);
        LoginResDto loginResDto = new LoginResDto(token);
        return ResponseEntity.ok(loginResDto);
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader("Authorization") String token) {
        String authToken = JwtUtil.extractToken(token);
        boolean loggedIn = jwtUtil.validateToken(authToken);
        if (!loggedIn) {
            throw new RuntimeException("Unauthorized");
        }
        UserDto userDto = authService.getUserfromToken(authToken);
        return ResponseEntity.ok(userDto);
    }

}

package com.educonnect.api.controller;

import com.educonnect.api.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.api.dto.LoginReqDto;
import com.educonnect.api.dto.LoginResDto;
import com.educonnect.api.dto.RegRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
    public String getUserInfo(@RequestParam String param) {
        return new String();
    }

}

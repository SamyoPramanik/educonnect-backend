package com.educonnect.auth_service.controller;

import com.educonnect.auth_service.service.AuthService;
import com.educonnect.auth_service.service.SessionService;
import com.educonnect.auth_service.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.auth_service.dto.LoginReqDto;
import com.educonnect.auth_service.dto.LoginResDto;
import com.educonnect.auth_service.dto.RegRequestDto;
import com.educonnect.auth_service.dto.SessionDto;
import com.educonnect.auth_service.dto.UserDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final SessionService sessionService;

    public AuthController(AuthService authService, JwtUtil jwtUtil, SessionService sessionService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.sessionService = sessionService;
    }

    @PostMapping("/register")
    @Tag(name = "Register a new user")
    @Operation(summary = "Register a new user")
    public void register(@Valid @RequestBody RegRequestDto regRequestDto) {
        authService.registerUser(regRequestDto);
    }

    @PostMapping("/login")
    @Tag(name = "Login a user")
    @Operation(summary = "Login a user")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto loginReqDto) {
        String token = authService.loginUser(loginReqDto);
        LoginResDto loginResDto = new LoginResDto(token);
        return ResponseEntity.ok(loginResDto);
    }

    @GetMapping("/logout")
    @Tag(name = "Logout a user")
    @Operation(summary = "Logout current session")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String authToken = JwtUtil.extractToken(token);
        boolean loggedIn = jwtUtil.validateToken(authToken);
        if (!loggedIn) {
            throw new RuntimeException("Unauthorized");
        }
        authService.logoutUser(authToken);
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/logout-all")
    @Tag(name = "Logout from all sessions")
    @Operation(summary = "Logout from all sessions")
    public ResponseEntity<String> logoutAll(@RequestHeader("Authorization") String token) {
        String authToken = JwtUtil.extractToken(token);
        boolean loggedIn = jwtUtil.validateToken(authToken);
        if (!loggedIn) {
            throw new RuntimeException("Unauthorized");
        }
        UUID userId = jwtUtil.getIdFromToken(authToken);
        authService.logoutAllSessions(userId);
        return ResponseEntity.ok("Logged out from all sessions");
    }

    @GetMapping("/logout-session/{sessionId}")
    @Tag(name = "Logout from a specific session")
    @Operation(summary = "Logout from a specific session")
    public ResponseEntity<String> logoutSession(@PathVariable String sessionId) {
        sessionService.invalidateSession(sessionId);
        return ResponseEntity.ok("Logged out from session " + sessionId);
    }

    @GetMapping("/userinfo")
    @Tag(name = "Get user information")
    @Operation(summary = "Get user information from token")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader("Authorization") String token) {
        String authToken = JwtUtil.extractToken(token);
        boolean loggedIn = jwtUtil.validateToken(authToken);
        if (!loggedIn) {
            throw new RuntimeException("Unauthorized");
        }
        boolean sessionValid = sessionService.isSessionValid(authToken);
        if (!sessionValid) {
            throw new RuntimeException("Session invalidated");
        }
        UserDto userDto = authService.getUserfromToken(authToken);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/sessions")
    @Tag(name = "Get user sessions")
    @Operation(summary = "Get all active sessions for the user")
    public ResponseEntity<List<SessionDto>> getUserSessions(@RequestHeader("Authorization") String token) {
        String authToken = JwtUtil.extractToken(token);
        boolean loggedIn = jwtUtil.validateToken(authToken);
        if (!loggedIn) {
            throw new RuntimeException("Unauthorized");
        }
        boolean sessionValid = sessionService.isSessionValid(authToken);
        if (!sessionValid) {
            throw new RuntimeException("Session invalidated");
        }
        List<SessionDto> sessions = sessionService.getUserSessions(authToken);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/update-status/{userId}/{status}")
    @Tag(name = "Update user status")
    @Operation(summary = "Update user status (ADMIN only)")
    public ResponseEntity<String> updateUserStatus(@RequestHeader("Authorization") String token,
            @PathVariable String userId, @PathVariable String status) {
        String authToken = JwtUtil.extractToken(token);
        String role = jwtUtil.getRoleFromToken(authToken);
        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Unauthorized");
        }
        boolean sessionValid = sessionService.isSessionValid(authToken);
        if (!sessionValid) {
            throw new RuntimeException("Session invalidated");
        }
        authService.changeUserStatus(UUID.fromString(userId), status);
        return ResponseEntity.ok("User status updated successfully");
    }

}

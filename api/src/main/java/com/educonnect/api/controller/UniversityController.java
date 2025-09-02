package com.educonnect.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.api.dto.UniversityDto;
import com.educonnect.api.model.University;
import com.educonnect.api.service.UniversityService;
import com.educonnect.api.util.JwtUtil;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/university")
public class UniversityController {
    private final JwtUtil jwtUtil;
    private final UniversityService universityService;

    public UniversityController(JwtUtil jwtUtil, UniversityService universityService) {
        this.jwtUtil = jwtUtil;
        this.universityService = universityService;
    }

    @PostMapping("/create")
    public ResponseEntity<UniversityDto> createUniversity(@RequestHeader("Authorization") String token,
            @Valid @RequestBody UniversityDto universityDto) {
        String authToken = JwtUtil.extractToken(token);
        String role = jwtUtil.getRoleFromToken(authToken);
        if (!role.equals("ADMIN") && !role.equals("MODERATOR")) {
            throw new RuntimeException("Unauthorized");
        }

        System.out.println("Creating university: " + universityDto.getName());

        UniversityDto createdUniversity = universityService.createUniversity(universityDto);
        return ResponseEntity.ok(createdUniversity);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UniversityDto> getUniversityById(@RequestHeader("Authorization") String token,
            @PathVariable String id) {
        String authToken = JwtUtil.extractToken(token);
        Boolean loggedIn = jwtUtil.validateToken(authToken);
        if (!loggedIn) {
            throw new RuntimeException("Unauthorized");
        }
        Optional<UniversityDto> university = universityService
                .getUniversityById(UUID.fromString(id));
        return university.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UniversityDto> updateUniversity(@RequestHeader("Authorization") String token,
            @Valid @RequestBody UniversityDto universityDto, @PathVariable String id) {
        String authToken = JwtUtil.extractToken(token);
        String role = jwtUtil.getRoleFromToken(authToken);
        if (!role.equals("ADMIN") && !role.equals("MODERATOR")) {
            throw new RuntimeException("Unauthorized");
        }
        UniversityDto updatedUniversity = universityService.updateUniversity(UUID.fromString(id),
                universityDto);
        return ResponseEntity.ok(updatedUniversity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@RequestHeader("Authorization") String token,
            @PathVariable String id) {
        String authToken = JwtUtil.extractToken(token);
        String role = jwtUtil.getRoleFromToken(authToken);
        if (!role.equals("ADMIN") && !role.equals("MODERATOR")) {
            throw new RuntimeException("Unauthorized");
        }
        universityService.deleteUniversity(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<UniversityDto>> getAllUniversities(@RequestHeader("Authorization") String token) {
        String authToken = JwtUtil.extractToken(token);
        Boolean loggedIn = jwtUtil.validateToken(authToken);
        if (!loggedIn) {
            throw new RuntimeException("Unauthorized");
        }
        List<UniversityDto> universities = universityService.getAllUniversities();
        return ResponseEntity.ok(universities);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UniversityDto>> searchUniversities(@RequestHeader("Authorization") String token,
            @RequestParam String query) {
        String authToken = JwtUtil.extractToken(token);
        Boolean loggedIn = jwtUtil.validateToken(authToken);
        if (!loggedIn) {
            throw new RuntimeException("Unauthorized");
        }
        List<UniversityDto> universities = universityService.searchUniversitiesByName(query);
        return ResponseEntity.ok(universities);
    }

}

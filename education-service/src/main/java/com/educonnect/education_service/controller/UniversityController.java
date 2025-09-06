package com.educonnect.education_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.educonnect.education_service.dto.UniversityDto;
import com.educonnect.education_service.dto.UserDto;
import com.educonnect.education_service.model.University;
import com.educonnect.education_service.service.UniversityService;
import com.educonnect.education_service.util.UserUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final UniversityService universityService;
    private final UserUtil userUtil;

    public UniversityController(UniversityService universityService, UserUtil userUtil) {
        this.universityService = universityService;
        this.userUtil = userUtil;
    }

    @PostMapping("/create")
    @Tag(name = "Create a new university")
    @Operation(summary = "Create a new university")
    public ResponseEntity<UniversityDto> createUniversity(@RequestHeader("Authorization") String token,
            @Valid @RequestBody UniversityDto universityDto) {

        UserDto user = userUtil.getUser(token);
        String role = user.getRole();

        if (!role.equals("ADMIN") && !role.equals("MODERATOR")) {
            throw new RuntimeException("Unauthorized");
        }

        System.out.println("Creating university: " + universityDto.getName());

        UniversityDto createdUniversity = universityService.createUniversity(universityDto);
        return ResponseEntity.ok(createdUniversity);
    }

    @PostMapping("/{id}")
    @Tag(name = "Get university by ID")
    @Operation(summary = "Get university by ID")
    public ResponseEntity<UniversityDto> getUniversityById(@RequestHeader("Authorization") String token,
            @PathVariable String id) {
        UserDto user = userUtil.getUser(token);
        if (user == null) {
            throw new RuntimeException("Unauthorized");
        }
        Optional<UniversityDto> university = universityService
                .getUniversityById(UUID.fromString(id));
        return university.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Tag(name = "Update an existing university")
    @Operation(summary = "Update an existing university")
    public ResponseEntity<UniversityDto> updateUniversity(@RequestHeader("Authorization") String token,
            @Valid @RequestBody UniversityDto universityDto, @PathVariable String id) {

        UserDto user = userUtil.getUser(token);
        String role = user.getRole();

        if (!role.equals("ADMIN") && !role.equals("MODERATOR")) {
            throw new RuntimeException("Unauthorized");
        }
        UniversityDto updatedUniversity = universityService.updateUniversity(UUID.fromString(id),
                universityDto);
        return ResponseEntity.ok(updatedUniversity);
    }

    @DeleteMapping("/{id}")
    @Tag(name = "Delete a university")
    @Operation(summary = "Delete a university by ID")
    public ResponseEntity<Void> deleteUniversity(@RequestHeader("Authorization") String token,
            @PathVariable String id) {
        UserDto user = userUtil.getUser(token);
        String role = user.getRole();

        if (!role.equals("ADMIN") && !role.equals("MODERATOR")) {
            throw new RuntimeException("Unauthorized");
        }
        universityService.deleteUniversity(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @Tag(name = "Get all universities")
    @Operation(summary = "Get all universities")
    public ResponseEntity<List<UniversityDto>> getAllUniversities(@RequestHeader("Authorization") String token) {
        UserDto user = userUtil.getUser(token);
        if (user == null) {
            throw new RuntimeException("Unauthorized");
        }
        List<UniversityDto> universities = universityService.getAllUniversities();
        return ResponseEntity.ok(universities);
    }

    @GetMapping("/search")
    @Tag(name = "Search universities by name")
    @Operation(summary = "Search universities by name")
    public ResponseEntity<List<UniversityDto>> searchUniversities(@RequestHeader("Authorization") String token,
            @RequestParam String query) {
        UserDto user = userUtil.getUser(token);
        if (user == null) {
            throw new RuntimeException("Unauthorized");
        }
        List<UniversityDto> universities = universityService.searchUniversitiesByName(query);
        return ResponseEntity.ok(universities);
    }
}

package com.educonnect.education_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.education_service.dto.ScholarshipDto;
import com.educonnect.education_service.service.ScholarshipService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/scholarships")
public class ScholarshipController {
    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }

    @PostMapping("/create")
    public ResponseEntity<ScholarshipDto> createScholarship(@Valid @RequestBody ScholarshipDto scholarshipDto) {
        ScholarshipDto createdScholarship = scholarshipService.createScholarship(scholarshipDto);
        return ResponseEntity.ok(createdScholarship);
    }
}

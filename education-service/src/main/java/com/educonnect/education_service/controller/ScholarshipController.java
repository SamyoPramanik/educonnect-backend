package com.educonnect.education_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.education_service.dto.ScholarshipDto;
import com.educonnect.education_service.service.ScholarshipService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/scholarship")
public class ScholarshipController {
    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }

    @PostMapping("/create")
    @Tag(name = "Create a new scholarship")
    @Operation(summary = "Create Scholarship", description = "Creates a new scholarship with the provided details.")
    public ResponseEntity<ScholarshipDto> createScholarship(@Valid @RequestBody ScholarshipDto scholarshipDto) {
        ScholarshipDto createdScholarship = scholarshipService.createScholarship(scholarshipDto);
        return ResponseEntity.ok(createdScholarship);
    }

    @PostMapping("/update/{id}")
    @Tag(name = "Update an existing scholarship")
    @Operation(summary = "Update Scholarship", description = "Updates an existing scholarship with the provided details.")
    public ResponseEntity<ScholarshipDto> updateScholarship(@Valid @RequestBody ScholarshipDto scholarshipDto,
            @RequestParam String id) {
        ScholarshipDto updatedScholarship = scholarshipService.updateScholarship(id, scholarshipDto);
        return ResponseEntity.ok(updatedScholarship);
    }

    @GetMapping("/get/{scholarshipId}")
    @Tag(name = "Get scholarship by ID")
    @Operation(summary = "Get Scholarship by ID", description = "Retrieves a scholarship by its ID.")
    public ResponseEntity<ScholarshipDto> getScholarshipById(@RequestParam String scholarshipId) {
        ScholarshipDto scholarshipDto = scholarshipService.getScholarshipById(scholarshipId);
        return ResponseEntity.ok(scholarshipDto);
    }

    @GetMapping("/all")
    @Tag(name = "Get all scholarships")
    @Operation(summary = "Get All Scholarships", description = "Retrieves all scholarships.")
    public ResponseEntity<List<ScholarshipDto>> getAllScholarships() {
        List<ScholarshipDto> scholarships = scholarshipService.getAllScholarships();
        return ResponseEntity.ok(scholarships);
    }

    @GetMapping("/by-university/{universityId}")
    @Tag(name = "Get scholarships by university ID")
    @Operation(summary = "Get Scholarships by University ID", description = "Retrieves scholarships by university ID.")
    public ResponseEntity<List<ScholarshipDto>> getScholarshipsByUniversityId(@RequestParam String universityId) {
        List<ScholarshipDto> scholarships = scholarshipService.getScholarshipsByUniversityId(universityId);
        return ResponseEntity.ok(scholarships);
    }

    @GetMapping("/by-field/{fieldId}")
    @Tag(name = "Get scholarships by field ID")
    @Operation(summary = "Get Scholarships by Field ID", description = "Retrieves scholarships by field ID.")
    public ResponseEntity<List<ScholarshipDto>> getScholarshipsByFieldId(@RequestParam String fieldId) {
        List<ScholarshipDto> scholarships = scholarshipService.getScholarshipsByFieldId(fieldId);
        return ResponseEntity.ok(scholarships);
    }

}

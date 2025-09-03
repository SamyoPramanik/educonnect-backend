package com.educonnect.education_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.educonnect.education_service.dto.ExperienceDto;
import com.educonnect.education_service.dto.FieldDto;
import com.educonnect.education_service.dto.PaperDto;
import com.educonnect.education_service.dto.ProfessorDetailsDto;
import com.educonnect.education_service.dto.ProfessorDto;
import com.educonnect.education_service.dto.ProfessorShortDetailsDto;
import com.educonnect.education_service.service.ProfessorService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProfessorShortDetailsDto>> getAllProfessors() {
        List<ProfessorShortDetailsDto> professors = professorService.getProfessors();
        return ResponseEntity.ok(professors);
    }

    @GetMapping("/{profId}")
    public ResponseEntity<ProfessorDetailsDto> getProfessor(@PathVariable String profId) {
        ProfessorDetailsDto professor = professorService.getProfessor(profId);
        if (professor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(professor);
    }

    @PostMapping("/new")
    public ResponseEntity<ProfessorDto> createProfessor(@Valid @RequestBody ProfessorDto professorDto) {
        ProfessorDto createdProfessor = professorService.createProfessor(professorDto);
        return ResponseEntity.ok(createdProfessor);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfessorDto> updateProfessor(@PathVariable String id,
            @Valid @RequestBody ProfessorDto professorDto) {
        ProfessorDto updatedProfessor = professorService.updateProfessor(id, professorDto);
        return ResponseEntity.ok(updatedProfessor);
    }

    @PostMapping("/{profId}/add-experience")
    public ResponseEntity<String> addExperience(@PathVariable String profId,
            @Valid @RequestBody ExperienceDto experienceDto) {
        professorService.addExperience(profId, experienceDto);
        return ResponseEntity.ok("Experience added");
    }

    @GetMapping("{profId}/delete-experience/{expId}")
    public ResponseEntity<String> deleteExperience(@PathVariable String profId, @PathVariable String expId) {
        professorService.deleteExperience(profId, expId);
        return ResponseEntity.ok("Experience deleted");
    }

    @PostMapping("/{profId}/add-paper")
    public ResponseEntity<String> addPaper(@PathVariable String profId, @Valid @RequestBody PaperDto paperDto) {
        professorService.addPaper(profId, paperDto);
        return ResponseEntity.ok("Paper added");
    }

    @GetMapping("/{profId}/delete-paper/{paperId}")
    public ResponseEntity<String> deletePaper(@PathVariable String profId, @PathVariable String paperId) {
        professorService.deletePaper(profId, paperId);
        return ResponseEntity.ok("Paper deleted");
    }

    @PostMapping("/{profId}/add-field")
    public ResponseEntity<String> addField(@PathVariable String profId, @RequestBody List<String> fieldDtos) {
        professorService.addField(profId, fieldDtos);
        return ResponseEntity.ok("Field added");
    }

    // @GetMapping("/{profId}/delete-field/{fieldId}")
    // public ResponseEntity<String> deleteField(@PathVariable String profId,
    // @PathVariable String fieldId) {
    // professorService.deleteField(profId, fieldId);
    // return ResponseEntity.ok("Field deleted");
    // }

}

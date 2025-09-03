package com.educonnect.education_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.education_service.dto.FieldDto;
import com.educonnect.education_service.service.FieldService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/fields")
public class FieldController {
    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/{id}")
    public FieldDto getField(@PathVariable String id) {
        return fieldService.getFieldById(id);
    }

    @PostMapping("/create")
    public FieldDto createField(@Valid @RequestBody FieldDto fieldDto) {
        return fieldService.createField(fieldDto);
    }

    @PutMapping("/{id}")
    public FieldDto updateField(@PathVariable String id, @Valid @RequestBody FieldDto fieldDto) {
        return fieldService.updateField(id, fieldDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteField(@PathVariable String id) {
        return fieldService.deleteField(id);
    }

}

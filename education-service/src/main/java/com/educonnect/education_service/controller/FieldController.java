package com.educonnect.education_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.education_service.dto.FieldDto;
import com.educonnect.education_service.service.FieldService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

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

    @GetMapping("/all")
    @Tag(name = "Get all fields")
    @Operation(summary = "Get all fields")
    public List<FieldDto> getAllFields() {
        return fieldService.getAllFields();
    }

    @GetMapping("/{id}")
    @Tag(name = "Get field by ID")
    @Operation(summary = "Get field by ID")
    public FieldDto getField(@PathVariable String id) {
        return fieldService.getFieldById(id);
    }

    @PostMapping("/create")
    @Tag(name = "Create a new field")
    @Operation(summary = "Create a new field")
    public FieldDto createField(@Valid @RequestBody FieldDto fieldDto) {
        return fieldService.createField(fieldDto);
    }

    @PutMapping("/{id}")
    @Tag(name = "Update an existing field")
    @Operation(summary = "Update an existing field")
    public FieldDto updateField(@PathVariable String id, @Valid @RequestBody FieldDto fieldDto) {
        return fieldService.updateField(id, fieldDto);
    }

    @DeleteMapping("/{id}")
    @Tag(name = "Delete a field")
    @Operation(summary = "Delete a field by ID")
    public boolean deleteField(@PathVariable String id) {
        return fieldService.deleteField(id);
    }

}

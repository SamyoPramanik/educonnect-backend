package com.educonnect.education_service.service;

import com.educonnect.education_service.model.Field;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.educonnect.education_service.dto.FieldDto;
import com.educonnect.education_service.repository.FieldRepository;

@Service
public class FieldService {
    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public FieldDto createField(FieldDto fieldDto) {
        Field field = new Field();
        field.setTitle(fieldDto.getTitle());
        Field newField = fieldRepository.save(field);
        fieldDto.setId(newField.getId().toString());
        return fieldDto;
    }

    public FieldDto getFieldById(String id) {
        Field field = fieldRepository.findById(UUID.fromString(id)).orElse(null);
        if (field == null) {
            return null;
        }
        FieldDto fieldDto = new FieldDto(field.getId().toString(), field.getTitle());
        return fieldDto;
    }

    public List<FieldDto> getAllFields() {
        List<Field> fields = fieldRepository.findAll();
        List<FieldDto> fieldDtos = fields.stream()
                .map(field -> new FieldDto(field.getId().toString(), field.getTitle()))
                .toList();
        return fieldDtos;
    }

    public FieldDto updateField(String id, FieldDto fieldDto) {
        Field field = fieldRepository.findById(UUID.fromString(id)).orElse(null);
        if (field == null) {
            return null;
        }
        field.setTitle(fieldDto.getTitle());
        Field updatedField = fieldRepository.save(field);
        fieldDto.setId(updatedField.getId().toString());
        return fieldDto;
    }

    public boolean deleteField(String id) {
        Field field = fieldRepository.findById(UUID.fromString(id)).orElse(null);
        if (field == null) {
            return false;
        }
        fieldRepository.delete(field);
        return true;
    }
}

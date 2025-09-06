package com.educonnect.education_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.educonnect.education_service.dto.ScholarshipDto;
import com.educonnect.education_service.dto.UniversityDto;
import com.educonnect.education_service.model.Scholarship;
import com.educonnect.education_service.model.ScholarshipField;
import com.educonnect.education_service.model.University;
import com.educonnect.education_service.repository.ScholarshipFieldRepository;
import com.educonnect.education_service.repository.ScholarshipRepository;

@Service
public class ScholarshipService {
    private final ScholarshipRepository scholarshipRepository;
    private final ScholarshipFieldRepository scholarshipFieldRepository;
    private final UniversityService universityService;

    public ScholarshipService(ScholarshipRepository scholarshipRepository,
            ScholarshipFieldRepository scholarshipFieldRepository, UniversityService universityService) {
        this.scholarshipRepository = scholarshipRepository;
        this.scholarshipFieldRepository = scholarshipFieldRepository;
        this.universityService = universityService;
    }

    public ScholarshipDto createScholarship(ScholarshipDto scholarshipDto) {
        // Logic to create a scholarship
        Scholarship scholarship = new Scholarship();
        scholarship.setTitle(scholarshipDto.getTitle());
        scholarship.setDescription(scholarshipDto.getDescription());
        scholarship.setAmount(scholarshipDto.getAmount());
        scholarship.setUniversityId(UUID.fromString(scholarshipDto.getUniversityId()));
        scholarship = scholarshipRepository.save(scholarship);
        scholarshipDto.setId(scholarship.getId().toString());

        List<String> fieldIds = scholarshipDto.getFields();
        for (String fieldIdString : fieldIds) {
            ScholarshipField scholarshipField = new ScholarshipField();
            scholarshipField.setScholarshipId(scholarship.getId());
            scholarshipField.setFieldId(UUID.fromString(fieldIdString));
            scholarshipFieldRepository.save(scholarshipField);
        }
        return scholarshipDto;
    }

    public ScholarshipDto getScholarshipById(String scholarshipId) {
        UUID schId = UUID.fromString(scholarshipId);
        Scholarship scholarship = scholarshipRepository.findById(schId)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));

        ScholarshipDto dto = new ScholarshipDto();
        dto.setId(scholarship.getId().toString());
        dto.setTitle(scholarship.getTitle());
        dto.setDescription(scholarship.getDescription());
        dto.setAmount(scholarship.getAmount());
        UUID universityId = scholarship.getUniversityId();
        String universityName = getUniversityName(universityId);
        dto.setUniversity(universityName);
        List<ScholarshipField> scholarshipFields = scholarshipFieldRepository
                .findByScholarshipId(scholarship.getId());
        List<String> fieldIds = scholarshipFields.stream()
                .map(field -> field.getFieldId().toString())
                .toList();
        dto.setFields(fieldIds);
        return dto;
    }

    public ScholarshipDto updateScholarship(String scholarshipId, ScholarshipDto scholarshipDto) {
        UUID schId = UUID.fromString(scholarshipId);
        Scholarship scholarship = scholarshipRepository.findById(schId)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));

        scholarship.setTitle(scholarshipDto.getTitle());
        scholarship.setDescription(scholarshipDto.getDescription());
        scholarship.setAmount(scholarshipDto.getAmount());
        scholarship = scholarshipRepository.save(scholarship);

        List<ScholarshipField> existingFields = scholarshipFieldRepository.findByScholarshipId(scholarship.getId());
        scholarshipFieldRepository.deleteAll(existingFields);
        List<String> fieldIds = scholarshipDto.getFields();
        for (String fieldIdString : fieldIds) {
            ScholarshipField scholarshipField = new ScholarshipField();
            scholarshipField.setScholarshipId(scholarship.getId());
            scholarshipField.setFieldId(UUID.fromString(fieldIdString));
            scholarshipFieldRepository.save(scholarshipField);
        }
        scholarshipDto.setId(scholarship.getId().toString());
        return scholarshipDto;
    }

    public List<ScholarshipDto> getScholarshipsByUniversityId(String universityId) {
        List<Scholarship> scholarships = scholarshipRepository.findByUniversityId(UUID.fromString(universityId));
        return scholarships.stream().map(scholarship -> {
            ScholarshipDto dto = new ScholarshipDto();
            dto.setId(scholarship.getId().toString());
            dto.setTitle(scholarship.getTitle());
            dto.setDescription(scholarship.getDescription());
            dto.setAmount(scholarship.getAmount());
            UUID uId = scholarship.getUniversityId();
            String universityName = getUniversityName(uId);
            dto.setUniversity(universityName);
            List<ScholarshipField> scholarshipFields = scholarshipFieldRepository
                    .findByScholarshipId(scholarship.getId());
            List<String> fieldIds = scholarshipFields.stream()
                    .map(field -> field.getFieldId().toString())
                    .toList();
            dto.setFields(fieldIds);
            return dto;
        }).toList();
    }

    public List<ScholarshipDto> getAllScholarships() {
        List<Scholarship> scholarships = scholarshipRepository.findAll();
        return scholarships.stream().map(scholarship -> {
            ScholarshipDto dto = new ScholarshipDto();
            dto.setId(scholarship.getId().toString());
            dto.setTitle(scholarship.getTitle());
            dto.setDescription(scholarship.getDescription());
            dto.setAmount(scholarship.getAmount());

            UUID universityId = scholarship.getUniversityId();
            String universityName = getUniversityName(universityId);
            dto.setUniversity(universityName);

            List<ScholarshipField> scholarshipFields = scholarshipFieldRepository
                    .findByScholarshipId(scholarship.getId());
            List<String> fieldIds = scholarshipFields.stream()
                    .map(field -> field.getFieldId().toString())
                    .toList();
            dto.setFields(fieldIds);
            return dto;
        }).toList();
    }

    public List<ScholarshipDto> getScholarshipsByFieldId(String fieldId) {
        List<ScholarshipField> scholarshipFields = scholarshipFieldRepository
                .findByFieldId(UUID.fromString(fieldId));
        return scholarshipFields.stream().map(scholarshipField -> {
            Scholarship scholarship = scholarshipRepository.findById(scholarshipField.getScholarshipId())
                    .orElseThrow(() -> new RuntimeException("Scholarship not found"));
            ScholarshipDto dto = new ScholarshipDto();
            dto.setId(scholarship.getId().toString());
            dto.setTitle(scholarship.getTitle());
            dto.setDescription(scholarship.getDescription());
            dto.setAmount(scholarship.getAmount());
            UUID universityId = scholarship.getUniversityId();
            String universityName = getUniversityName(universityId);
            dto.setUniversity(universityName);
            List<ScholarshipField> fields = scholarshipFieldRepository
                    .findByScholarshipId(scholarship.getId());
            List<String> fieldIds = fields.stream()
                    .map(field -> field.getFieldId().toString())
                    .toList();
            dto.setFields(fieldIds);
            return dto;
        }).toList();
    }

    public void deleteScholarship(String scholarshipId) {
        UUID schId = UUID.fromString(scholarshipId);
        scholarshipFieldRepository.deleteAll(scholarshipFieldRepository.findByScholarshipId(schId));
        scholarshipRepository.deleteById(schId);
    }

    public String getUniversityName(UUID universityId) {
        Optional<UniversityDto> university = universityService.getUniversityById(universityId);
        if (university.isPresent()) {
            return university.get().getName();
        }
        return "Unknown University";
    }
}

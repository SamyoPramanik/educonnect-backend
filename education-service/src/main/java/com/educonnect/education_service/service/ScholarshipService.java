package com.educonnect.education_service.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.educonnect.education_service.dto.FieldDto;
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
    private final FieldService fieldService;

    public ScholarshipService(ScholarshipRepository scholarshipRepository,
            ScholarshipFieldRepository scholarshipFieldRepository, UniversityService universityService,
            FieldService fieldService) {
        this.scholarshipRepository = scholarshipRepository;
        this.scholarshipFieldRepository = scholarshipFieldRepository;
        this.universityService = universityService;
        this.fieldService = fieldService;
    }

    public ScholarshipDto createScholarship(ScholarshipDto scholarshipDto) {
        // Logic to create a scholarship
        Scholarship scholarship = new Scholarship();
        scholarship.setTitle(scholarshipDto.getTitle());
        scholarship.setDescription(scholarshipDto.getDescription());
        scholarship.setAmount(Integer.parseInt(scholarshipDto.getAmount()));
        scholarship.setDeadline(LocalDate.parse(scholarshipDto.getDeadline()));
        scholarship.setUniversityId(UUID.fromString(scholarshipDto.getUniversityId()));
        scholarship = scholarshipRepository.save(scholarship);
        scholarshipDto.setId(scholarship.getId().toString());

        List<FieldDto> fields = scholarshipDto.getFields();
        for (FieldDto field : fields) {
            ScholarshipField scholarshipField = new ScholarshipField();
            scholarshipField.setScholarshipId(scholarship.getId());
            scholarshipField.setFieldId(UUID.fromString(field.getId()));
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
        dto.setAmount(String.valueOf(scholarship.getAmount()));
        UUID universityId = scholarship.getUniversityId();
        String universityName = getUniversityName(universityId);
        dto.setUniversity(universityName);
        List<ScholarshipField> scholarshipFields = scholarshipFieldRepository
                .findByScholarshipId(scholarship.getId());
        List<FieldDto> fields = new ArrayList<>();
        for (ScholarshipField field : scholarshipFields) {
            FieldDto fieldDto = fieldService.getFieldById(field.getFieldId().toString());
            if (fieldDto != null)
                fields.add(fieldDto);
        }
        dto.setFields(fields);
        return dto;
    }

    public ScholarshipDto updateScholarship(String scholarshipId, ScholarshipDto scholarshipDto) {
        UUID schId = UUID.fromString(scholarshipId);
        Scholarship scholarship = scholarshipRepository.findById(schId)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));

        scholarship.setTitle(scholarshipDto.getTitle());
        scholarship.setDescription(scholarshipDto.getDescription());
        scholarship.setAmount(Integer.parseInt(scholarshipDto.getAmount()));
        scholarship.setUniversityId(UUID.fromString(scholarshipDto.getUniversityId()));
        scholarship = scholarshipRepository.save(scholarship);

        List<ScholarshipField> existingFields = scholarshipFieldRepository.findByScholarshipId(scholarship.getId());
        scholarshipFieldRepository.deleteAll(existingFields);
        List<FieldDto> fields = scholarshipDto.getFields();
        for (FieldDto field : fields) {
            ScholarshipField scholarshipField = new ScholarshipField();
            scholarshipField.setScholarshipId(scholarship.getId());
            scholarshipField.setFieldId(UUID.fromString(field.getId()));
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
            dto.setAmount(String.valueOf(scholarship.getAmount()));
            UUID uId = scholarship.getUniversityId();
            String universityName = getUniversityName(uId);
            dto.setUniversity(universityName);
            List<ScholarshipField> scholarshipFields = scholarshipFieldRepository
                    .findByScholarshipId(scholarship.getId());
            List<FieldDto> fields = new ArrayList<>();
            for (ScholarshipField field : scholarshipFields) {
                FieldDto fieldDto = fieldService.getFieldById(field.getFieldId().toString());
                if (fieldDto != null)
                    fields.add(fieldDto);
            }
            dto.setFields(fields);
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
            dto.setAmount(String.valueOf(scholarship.getAmount()));
            dto.setUniversityId(scholarship.getUniversityId().toString());

            UUID universityId = scholarship.getUniversityId();
            String universityName = getUniversityName(universityId);
            dto.setUniversity(universityName);

            List<ScholarshipField> scholarshipFields = scholarshipFieldRepository
                    .findByScholarshipId(scholarship.getId());
            List<FieldDto> fields = new ArrayList<>();
            for (ScholarshipField field : scholarshipFields) {
                FieldDto fieldDto = fieldService.getFieldById(field.getFieldId().toString());
                if (fieldDto != null)
                    fields.add(fieldDto);
            }
            dto.setFields(fields);
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
            dto.setAmount(String.valueOf(scholarship.getAmount()));
            dto.setUniversityId(scholarship.getUniversityId().toString());
            UUID universityId = scholarship.getUniversityId();
            String universityName = getUniversityName(universityId);
            dto.setUniversity(universityName);
            List<ScholarshipField> sFields = scholarshipFieldRepository
                    .findByScholarshipId(scholarship.getId());
            List<FieldDto> fields = new ArrayList<>();
            for (ScholarshipField field : sFields) {
                FieldDto fieldDto = fieldService.getFieldById(field.getFieldId().toString());
                if (fieldDto != null)
                    fields.add(fieldDto);
            }
            dto.setFields(fields);
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

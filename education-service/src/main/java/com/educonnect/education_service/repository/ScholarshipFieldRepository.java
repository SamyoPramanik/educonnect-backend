package com.educonnect.education_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.education_service.model.ScholarshipField;

@Repository
public interface ScholarshipFieldRepository extends JpaRepository<ScholarshipField, UUID> {
    List<ScholarshipField> findByScholarshipId(UUID scholarshipId);

    List<ScholarshipField> findByFieldId(UUID fieldId);
}

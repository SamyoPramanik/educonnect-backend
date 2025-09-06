package com.educonnect.education_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.education_service.model.Scholarship;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, UUID> {
    List<Scholarship> findByUniversityId(UUID universityId);
}

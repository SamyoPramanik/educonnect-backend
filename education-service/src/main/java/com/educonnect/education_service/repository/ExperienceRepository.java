package com.educonnect.education_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.educonnect.education_service.model.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, UUID> {
    List<Experience> findByProfessorId(UUID professorId);

    // Current experience (endYear is NULL or 0)
    @Query("SELECT e FROM Experience e WHERE e.professorId = :professorId AND (e.endYear IS NULL OR e.endYear = 0)")
    Optional<Experience> findCurrentExperienceByProfessorId(UUID professorId);

    // Get current universityId of professor
    @Query("SELECT e.universityId FROM Experience e WHERE e.professorId = :professorId AND (e.endYear IS NULL OR e.endYear = 0)")
    Optional<UUID> findCurrentUniversityIdByProfessorId(UUID professorId);
}

package com.educonnect.education_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.educonnect.education_service.model.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, UUID> {
    public List<Paper> findByProfessorId(UUID professorId);

    // Count papers by professor ID
    @Query("SELECT COUNT(p) FROM Paper p WHERE p.professorId = :professorId")
    int countByProfessorId(UUID professorId);
}

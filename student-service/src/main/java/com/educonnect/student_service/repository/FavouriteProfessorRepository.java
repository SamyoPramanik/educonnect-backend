package com.educonnect.student_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.student_service.model.FavouriteProfessor;

@Repository
public interface FavouriteProfessorRepository extends JpaRepository<FavouriteProfessor, UUID> {
    List<FavouriteProfessor> findByStudentId(UUID studentId);

    List<FavouriteProfessor> findByProfessorId(UUID professorId);

    List<FavouriteProfessor> findByStudentIdAndProfessorId(UUID studentId, UUID professorId);
}

package com.educonnect.education_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.educonnect.education_service.model.Experience;
import com.educonnect.education_service.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, UUID> {

}

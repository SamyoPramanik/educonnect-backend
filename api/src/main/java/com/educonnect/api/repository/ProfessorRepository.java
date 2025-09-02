package com.educonnect.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.educonnect.api.model.Experience;
import com.educonnect.api.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, UUID> {

}

package com.educonnect.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.api.model.ProfessorField;

@Repository
public interface ProfessorFieldRepository extends JpaRepository<ProfessorField, UUID> {

}

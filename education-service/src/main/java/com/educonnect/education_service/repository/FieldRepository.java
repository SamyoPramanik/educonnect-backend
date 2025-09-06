package com.educonnect.education_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.education_service.model.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {
}

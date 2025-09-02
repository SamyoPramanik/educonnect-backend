package com.educonnect.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.api.model.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {
}

package com.educonnect.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.api.model.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, UUID> {
    Optional<University> findByName(String name);

    Optional<University> findByNameAndIdNot(String name, UUID id);

    List<University> findByNameContainingIgnoreCase(String name);

}

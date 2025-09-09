package com.educonnect.housing_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.housing_service.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID> {
    Owner findByUserId(UUID userId);
}

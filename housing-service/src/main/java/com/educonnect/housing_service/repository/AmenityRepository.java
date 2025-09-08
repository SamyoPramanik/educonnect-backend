package com.educonnect.housing_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.housing_service.model.Amenity;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, UUID> {

}

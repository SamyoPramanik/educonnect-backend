package com.educonnect.housing_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.housing_service.model.HouseAmenity;

@Repository
public interface HouseAmenityRepository extends JpaRepository<HouseAmenity, UUID> {
    List<HouseAmenity> findByHouseId(UUID houseId);
}

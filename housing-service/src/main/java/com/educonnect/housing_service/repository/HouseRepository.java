package com.educonnect.housing_service.repository;

import org.springframework.stereotype.Repository;

import com.educonnect.housing_service.model.House;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface HouseRepository extends JpaRepository<House, UUID> {

}

package com.educonnect.student_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.student_service.model.FavouriteHouse;

@Repository
public interface FavouriteHouseRepository extends JpaRepository<FavouriteHouse, UUID> {
    List<FavouriteHouse> findByStudentId(UUID studentId);

    List<FavouriteHouse> findByHouseId(UUID houseId);

    List<FavouriteHouse> findByStudentIdAndHouseId(UUID studentId, UUID houseId);
}

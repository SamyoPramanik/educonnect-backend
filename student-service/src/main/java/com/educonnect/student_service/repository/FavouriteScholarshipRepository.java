package com.educonnect.student_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.student_service.model.FavouriteScholarship;

@Repository
public interface FavouriteScholarshipRepository extends JpaRepository<FavouriteScholarship, UUID> {
    List<FavouriteScholarship> findByStudentId(UUID studentId);

    List<FavouriteScholarship> findByScholarshipId(UUID scholarshipId);

    List<FavouriteScholarship> findByStudentIdAndScholarshipId(UUID studentId, UUID scholarshipId);
}

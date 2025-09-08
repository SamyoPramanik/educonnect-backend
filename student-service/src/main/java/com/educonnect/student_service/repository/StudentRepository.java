package com.educonnect.student_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educonnect.student_service.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

}

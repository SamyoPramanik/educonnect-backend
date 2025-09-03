package com.educonnect.auth_service.repository;

import org.springframework.stereotype.Repository;

import com.educonnect.auth_service.model.User;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    java.util.Optional<User> findByEmail(String email);
}

package com.klu.cachecart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klu.cachecart.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
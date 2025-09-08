package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByInstitutionalEmail(String institutionalEmail);
}


package com.springeboot.example.ems.backend.repository;

import com.springeboot.example.ems.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
//    User findByUsername(String username);
    Optional<User> findByUsername(String username);
}

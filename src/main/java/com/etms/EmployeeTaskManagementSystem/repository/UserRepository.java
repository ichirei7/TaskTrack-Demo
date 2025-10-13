package com.etms.EmployeeTaskManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.etms.EmployeeTaskManagementSystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // useful for login/auth
 
}
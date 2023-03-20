package com.todowebapp.domain.user.repository;

import com.todowebapp.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

    Optional<Users> findByUsername(String username);

    Boolean existsByUsername(String username);

    Users findByUsernameAndPassword(String username, String password);
}

package com.todowebapp.domain.users.repository;

import com.todowebapp.domain.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Boolean existsByUsername(String username);

    Users findByUsernameAndPassword(String username, String password);
}

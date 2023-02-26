package com.todowebapp.service;

import com.todowebapp.model.UserEntity;
import com.todowebapp.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity create(final UserEntity userEntity){

        if(userEntity == null || userEntity.getUsername() == null){
            throw new RuntimeException("Invalid arguments");
        }

        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)) {
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder) {

        UserEntity originalUser = userRepository.findByUsername(username);

        if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }
}

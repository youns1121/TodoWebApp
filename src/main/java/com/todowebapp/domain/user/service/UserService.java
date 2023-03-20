package com.todowebapp.domain.user.service;

import com.todowebapp.domain.user.domain.UserAdapter;
import com.todowebapp.domain.user.domain.Users;
import com.todowebapp.domain.user.enums.UserEnums;
import com.todowebapp.domain.user.repository.UserRepository;
import com.todowebapp.domain.user.dto.UserDTO;
import com.todowebapp.util.SaltUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final SaltUtil saltUtil;

    @Transactional
    public UserEnums insertUser(final UserDTO userDTO){

        if(userDTO.getPassword() == null) {
            return UserEnums.INVALID_PASSWORD;
        }

        if(userRepository.existsByUsername(userDTO.getUsername())) {
            return UserEnums.USERNAME_ALREADY_EXISTS;
        }
        userRepository.save(Users.createUser(userDTO, saltUtil));
        return UserEnums.OK;
    }

    @Transactional(readOnly = true)
    public Users getByCredentials(final String username, final String password, final PasswordEncoder encoder) {

        Users originalUsers = userRepository.findByUsername(username).orElse(null);

        if(originalUsers != null && encoder.matches(password, originalUsers.getPassword())) {
            return originalUsers;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Users usersEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(UserEnums.USERNAME_NOT_FOUND.getValue()));

        return UserAdapter.from(usersEntity);
    }
}

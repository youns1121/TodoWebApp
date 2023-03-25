package com.todowebapp.domain.users.service;

import com.sun.jdi.request.DuplicateRequestException;
import com.todowebapp.domain.users.domain.UsersAdapter;
import com.todowebapp.domain.users.domain.Users;
import com.todowebapp.domain.users.enums.UserEnums;
import com.todowebapp.domain.users.repository.UserRepository;
import com.todowebapp.domain.users.dto.UsersDTO;
import com.todowebapp.exception.DataNotFoundException;
import com.todowebapp.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SaltUtil saltUtil;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    @Transactional
    public UsersDTO insertUser(final UsersDTO usersDTO){

        if(usersDTO.getPassword() == null) {
            throw new DataNotFoundException(UserEnums.INVALID_PASSWORD.getValue());
        }

        if(userRepository.existsByUsername(usersDTO.getUsername())) {
            throw new DuplicateRequestException(UserEnums.USERNAME_ALREADY_EXISTS.getValue());
        }

        return usersDTO.toDTO(userRepository.save(Users.createUser(usersDTO, saltUtil)));
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

        return UsersAdapter.from(usersEntity);
    }

    public UserEnums checkLoginUserStatus(Users users, HttpServletResponse response) {

        final String refreshToken = jwtUtil.createRefreshToken(users);
        redisUtil.setDataExpire(refreshToken, users.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
        cookieUtil.addHeaderCookie(response, JwtUtil.ACCESS_TOKEN_NAME, jwtUtil.createAccessToken(users));
        cookieUtil.addHeaderCookie(response, JwtUtil.REFRESH_TOKEN_NAME, refreshToken);
        return UserEnums.USER_LOGIN_SUCCESS;
    } 
}

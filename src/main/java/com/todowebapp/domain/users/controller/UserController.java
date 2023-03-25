package com.todowebapp.domain.users.controller;

import com.todowebapp.domain.users.enums.UserEnums;
import com.todowebapp.dto.ResponseDTO;
import com.todowebapp.domain.users.dto.UsersDTO;
import com.todowebapp.domain.users.domain.Users;
import com.todowebapp.domain.users.service.UsersService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(value = "User API", tags = "User API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody UsersDTO usersDTO) {
        try {
            return ResponseEntity.ok().body(usersService.insertUser(usersDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder().error(e.getMessage()).build());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody UsersDTO usersDTO, HttpServletResponse response) {

        Users users = usersService.getByCredentials(usersDTO.getUsername(), usersDTO.getPassword(), passwordEncoder);
        UserEnums userEnums = usersService.checkLoginUserStatus(users, response);

        if(users != null && UserEnums.USER_LOGIN_SUCCESS == userEnums) {
            usersDTO.setUsername(users.getUsername());
            usersDTO.setMessage(userEnums.getValue());
            return ResponseEntity.ok().body(usersDTO);
        }

        return ResponseEntity.badRequest().body(ResponseDTO.builder().error(UserEnums.FAIL.getValue()).build());
    }
}

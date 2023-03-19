package com.todowebapp.domain.user.controller;

import com.todowebapp.domain.user.enums.UserEnums;
import com.todowebapp.dto.ResponseDTO;
import com.todowebapp.domain.user.dto.UserDTO;
import com.todowebapp.domain.user.domain.UserEntity;
import com.todowebapp.enums.ResponseEnum;
import com.todowebapp.handler.ResponseHandler;
import com.todowebapp.security.TokenProvider;
import com.todowebapp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final ResponseHandler responseHandler;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody UserDTO userDTO) {

        UserEnums response = userService.insertUser(userDTO);
        if(response != UserEnums.OK) {
            return responseHandler.fail(response.getKey(), response.getValue());
        }
        return responseHandler.ok();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody UserDTO userDTO) {

        UserEntity user = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword(), passwordEncoder);

        if(user != null) {
            UserDTO responseUserDTO = userDTO.toDTO(user);
            responseUserDTO.setToken(tokenProvider.create(user));
            return ResponseEntity.ok().body(responseUserDTO);
        }

        return ResponseEntity.badRequest().body(ResponseDTO.builder().error("Login Failed").build());
    }
}

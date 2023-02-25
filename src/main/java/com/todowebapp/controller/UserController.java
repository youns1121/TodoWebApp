package com.todowebapp.controller;

import com.todowebapp.dto.ResponseDTO;
import com.todowebapp.dto.UserDTO;
import com.todowebapp.model.UserEntity;
import com.todowebapp.security.TokenProvider;
import com.todowebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {

        try{
            if(userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password Value");
            }
            UserEntity registeredUser = userService.create(UserEntity.createUser(userDTO));
            UserDTO responseUserDTO = userDTO.to(registeredUser);

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(ResponseDTO.builder().error(e.getMessage()).build());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {

        UserEntity user = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword());

        if(user != null) {

            UserDTO responseUserDTO = userDTO.to(user);
            responseUserDTO.setToken(tokenProvider.create(user));

            return ResponseEntity.ok().body(responseUserDTO);
        }

        return ResponseEntity
                .badRequest()
                .body(ResponseDTO.builder().error("Login Failed").build());
    }
}

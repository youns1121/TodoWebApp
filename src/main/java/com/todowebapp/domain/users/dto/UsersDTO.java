package com.todowebapp.domain.users.dto;

import com.todowebapp.domain.users.domain.Users;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersDTO {

    private String username;

    private String password;

    @ApiModelProperty(hidden = true)
    private String message;

    @Builder
    public UsersDTO(String username, String password, String message) {
        this.username = username;
        this.password = password;
        this.message = message;
    }

    public UsersDTO toDTO(Users users){

        return UsersDTO.builder()
                .username(users.getUsername())
                .build();
    }
}

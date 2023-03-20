package com.todowebapp.domain.user.dto;

import com.todowebapp.domain.user.domain.Users;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String username;

    private String password;

    @ApiModelProperty(hidden = true)
    private String token;

    @ApiModelProperty(hidden = true)
    private String id;

    @Builder
    public UserDTO(String username, String password, String token, String id) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.id = id;
    }

    public UserDTO toDTO(Users usersEntity){

        return UserDTO.builder()
                .id(usersEntity.getId())
                .username(usersEntity.getUsername())
                .build();
    }
}

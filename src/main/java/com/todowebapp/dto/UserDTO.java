package com.todowebapp.dto;

import com.todowebapp.domain.user.domain.UserEntity;
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
    public UserDTO(String token, String username, String password, String id) {
        this.token = token;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public UserDTO toDTO(UserEntity userEntity){

        return UserDTO.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .username(this.username)
                .password(this.password)
                .build();
    }
}

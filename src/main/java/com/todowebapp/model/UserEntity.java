package com.todowebapp.model;


import com.todowebapp.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "auth_provider")
    private String authProvider;

    @Builder
    public UserEntity(String id, String username, String password, String role, String authProvider) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.authProvider = authProvider;
    }

    public static UserEntity createUser(UserDTO userDTO) {

        return UserEntity.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
    }
}

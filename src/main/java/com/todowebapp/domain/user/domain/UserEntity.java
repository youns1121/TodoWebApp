package com.todowebapp.domain.user.domain;

import com.todowebapp.domain.Salt.Salt;
import com.todowebapp.domain.user.enums.UserRole;
import com.todowebapp.domain.user.dto.UserDTO;
import com.todowebapp.util.SaltUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salt_id", columnDefinition = "int comment '암호키 아이디'")
    private Salt salt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Set<UserRole> userRole;

    @Builder
    public UserEntity(String id, String username, String password, Salt salt, UserStatus userStatus, Set<UserRole> userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.userStatus = userStatus;
        this.userRole = userRole;
    }

    public static UserEntity createUser(UserDTO userDTO, SaltUtil saltUtil) {
        String salt = saltUtil.genSalt();
        return UserEntity.builder()
                .salt(new Salt(salt))
                .username(userDTO.getUsername())
                .password(saltUtil.encodePassword(salt, userDTO.getPassword()))
                .userRole(Collections.singleton(UserRole.USER))
                .userStatus(UserStatus.NORMAL)
                .build();
    }
}

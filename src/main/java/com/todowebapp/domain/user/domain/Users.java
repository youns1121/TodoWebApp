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
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class Users {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(name="regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'")
    private LocalDateTime regiDatetime;

    @Column(name="upd_datetime",  columnDefinition = "DATETIME(6) comment '수정 일시'")
    private LocalDateTime updDatetime;


    @Builder
    public Users(String id, String username, String password, Salt salt, UserStatus userStatus, UserRole userRole, LocalDateTime regiDatetime, LocalDateTime updDatetime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.userStatus = userStatus;
        this.userRole = userRole;
        this.regiDatetime = regiDatetime;
        this.updDatetime = updDatetime;
    }

    public static Users createUser(UserDTO userDTO, SaltUtil saltUtil) {
        String salt = saltUtil.genSalt();
        return Users.builder()
                .salt(new Salt(salt))
                .username(userDTO.getUsername())
                .password(saltUtil.encodePassword(salt, userDTO.getPassword()))
                .userRole(UserRole.ROLE_USER)
                .userStatus(UserStatus.NORMAL)
                .regiDatetime(LocalDateTime.now())
                .updDatetime(LocalDateTime.now())
                .build();
    }
}

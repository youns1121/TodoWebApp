package com.todowebapp.domain.Salt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "salt")
public class Salt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salt_id", columnDefinition = "int comment '암호키 아이디'")
    private int id;

    @Column(name = "salt_data", columnDefinition = "VARCHAR(255) comment '암호키 데이터'")
    private String salt;

    public Salt(String salt) {
        this.salt = salt;
    }
}

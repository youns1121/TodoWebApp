package com.todowebapp.domain.user.domain;

import com.todowebapp.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAdapter extends User {

    private UserEntity userEntity;


    public UserAdapter(UserEntity userEntity) {
        super(userEntity.getUsername(), userEntity.getPassword(), authorities(userEntity.getUserRole()));
        this.userEntity = userEntity;
    }

    public static UserAdapter from(UserEntity userEntity) {
        return new UserAdapter(userEntity);
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<UserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }
}

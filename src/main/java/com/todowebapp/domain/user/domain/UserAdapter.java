package com.todowebapp.domain.user.domain;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class UserAdapter extends User {

    private long userId;

    public UserAdapter(Users users) {
        super(users.getUsername(), users.getPassword(), AuthorityUtils.createAuthorityList(users.getUserRole().toString()));
    }

    public static UserAdapter from(Users users) {
        return new UserAdapter(users);
    }


    public long getUserId() {
        return userId;
    }
}

package com.todowebapp.domain.users.domain;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class UsersAdapter extends User {

    private static final long serialVersionUID = -8025565672925706245L;

    private long seq;

    private Users users;

    public UsersAdapter(Users users) {
        super(users.getUsername(), users.getPassword(), AuthorityUtils.createAuthorityList(users.getUserRole().toString()));
        setUsers(users);
        setUsersSeq(users.getSeq());
    }

    public static UsersAdapter from(Users users) {
        return new UsersAdapter(users);
    }

    public long getUsersSeq() {
        return seq;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setUsersSeq(long seq) {
        this.seq = seq;
    }
}
package com.todowebapp.util;

import com.todowebapp.domain.users.domain.UsersAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationUtil {

    public long getUsersSeq() {
        return getId("usersSeq");
    }


    private long getId(String id) {

        long returnId = 0;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(!(authentication.getPrincipal() instanceof String)) {
                UsersAdapter usersAdapter = (UsersAdapter) authentication.getPrincipal();
                if ("usersSeq".equals(id)) {
                    returnId = usersAdapter.getUsersSeq();
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return returnId;
    }
}

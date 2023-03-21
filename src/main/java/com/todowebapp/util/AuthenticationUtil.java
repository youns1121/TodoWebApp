package com.todowebapp.util;

import com.todowebapp.domain.user.domain.UserAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationUtil {

    public long getUserId() {
        return getId("userId");
    }


    private long getId(String id) {

        long returnId = 0;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(!(authentication.getPrincipal() instanceof String)) {
                UserAdapter userAdapter = (UserAdapter) authentication.getPrincipal();
                if ("userId".equals(id)) {
                    returnId = userAdapter.getUserId();
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return returnId;
    }
}

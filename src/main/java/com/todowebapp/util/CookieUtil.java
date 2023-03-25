package com.todowebapp.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class CookieUtil {

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();
        if(cookies == null) {
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

    public void addHeaderCookie(HttpServletResponse res, String cookieName, String value){
        res.addHeader("Set-Cookie", makeResponseCookie(cookieName, value));
    }

    private String makeResponseCookie(String cookieName, String value) {
        int maxAge = value == null ? 0 : (int) JwtUtil.ACCESS_TOKEN_VALIDATION_SECOND;
            return ResponseCookie.from(cookieName, value)
                    .httpOnly(true)
                    .sameSite("None")
                    .secure(true)
                    .maxAge(maxAge)
                    .path("/")
                    .build().toString();
        }
}

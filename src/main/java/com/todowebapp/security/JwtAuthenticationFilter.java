package com.todowebapp.security;

import com.todowebapp.domain.users.domain.Users;
import com.todowebapp.domain.users.service.UsersService;
import com.todowebapp.util.CookieUtil;
import com.todowebapp.util.JwtUtil;
import com.todowebapp.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final UsersService usersService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        final Cookie jwtToken = cookieUtil.getCookie(httpServletRequest, JwtUtil.ACCESS_TOKEN_NAME);
        String refreshJwt = null;
        String jwt;
        String username;
        try {
            if(jwtToken != null) {
                jwt = jwtToken.getValue();
                username = jwtUtil.getUsername(jwt);
            } else {
                username = redisUtil.getData(httpServletRequest.getRemoteAddr());
                jwt = jwtUtil.createAccessToken(Users.builder().username(username).build());
            }

            UserDetails userDetails = usersService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        } catch (ExpiredJwtException e) {
            Cookie refreshToken = cookieUtil.getCookie(httpServletRequest, JwtUtil.REFRESH_TOKEN_NAME);
            if(refreshToken!=null){
                refreshJwt = refreshToken.getValue();
            }

        } catch(Exception e) {
            log.error("Could not set user authentication in security context", e);
        }

        try {
            if (refreshJwt != null) {
                String refreshUsername = redisUtil.getData(refreshJwt);
                if (refreshUsername == null) {
                    redisUtil.deleteData(refreshJwt);
                    cookieUtil.addHeaderCookie(httpServletResponse, JwtUtil.REFRESH_TOKEN_NAME, null);
                } else {
                    if (refreshUsername.equals(jwtUtil.getUsername(refreshJwt))) {
                        UserDetails userDetails = usersService.loadUserByUsername(refreshUsername);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        cookieUtil.addHeaderCookie(httpServletResponse, JwtUtil.ACCESS_TOKEN_NAME, jwtUtil.createAccessToken(Users.createUsersToken(refreshUsername)));
                    }
                }
            }
        }catch (ExpiredJwtException e) {
            log.info(e.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

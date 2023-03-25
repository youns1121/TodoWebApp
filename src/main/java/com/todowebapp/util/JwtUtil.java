package com.todowebapp.util;

import com.todowebapp.domain.users.domain.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class JwtUtil {

    public final static long ACCESS_TOKEN_VALIDATION_SECOND = 60 * 60 * 24;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 60 * 60 * 24 * 7;
    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";
    
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String create(Users users) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(String.valueOf(users.getSeq()))
                .setIssuer("todo app")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .compact();
    }

    public String getUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }


    private Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Boolean isTodayIssuedAt(String token) throws ParseException {
        final Date issuedAt = extractAllClaims(token).getIssuedAt();
        return DateUtil.getBetweenDate(DateUtil.dateToString(issuedAt), DateUtil.dateToString(new Date())) == 0;
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws ParseException {
        final String username = getUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && isTodayIssuedAt(token));
    }

    public String createAccessToken(Users users) {
        return doCreateAccessToken(users.getUsername(), JwtUtil.ACCESS_TOKEN_VALIDATION_SECOND);
    }

    public String createRefreshToken(Users users) {
        return doCreateAccessToken(users.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
    }

    private String doCreateAccessToken(String username, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return buildJWT(claims, expireTime);
    }

    private String buildJWT(Claims claims, long expireTime){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (expireTime * 1000)))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }
}

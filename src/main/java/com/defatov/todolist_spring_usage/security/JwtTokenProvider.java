package com.defatov.todolist_spring_usage.security;

import com.defatov.todolist_spring_usage.model.User;
import com.defatov.todolist_spring_usage.service.impl.UserDetailsService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${auth.cookie.secret}")
    private String secretKey;

    @Getter
    @Value("${auth.cookie.auth}")
    private String authCookieName;

    @Getter
    @Value("${auth.cookie.refresh}")
    private String refreshCookieName;

    @Getter
    @Value("${auth.cookie.expiration-auth}")
    private String authExpirationCookie;

    @Getter
    @Value("${auth.cookie.expiration-refresh}")
    private String refreshExpirationCookie;

    @Getter
    @Value("${auth.cookie.path}")
    private String cookiePath;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAuthToken(String username, String role) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date valid = new Date(now.getTime() + getAuthExpirationCookie());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(valid)
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .compact();

    }

    public String createRefreshToken(String username, String role) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date valid = new Date(now.getTime() + getRefreshExpirationCookie());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(valid)
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .compact();

    }

    public boolean validateToken(String token) {

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build().parseClaimsJws(token);
            return claimsJws.getBody().getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            log.error(e.getLocalizedMessage());
        }

        return false;


    }

    public Authentication getAuthentication(String token) {

        User user = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

    }

    public String getUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody().getSubject();

    }

    public String resolveToken(HttpServletRequest httpServletRequest) {

        Cookie[] cookies = httpServletRequest.getCookies();
        String res = null;
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if(cookie.getName().equals(getAuthCookieName())) {
                    res = cookie.getValue();
                }
            }


        }

        return res;

    }

}

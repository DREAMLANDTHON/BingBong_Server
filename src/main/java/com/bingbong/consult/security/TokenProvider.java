package com.bingbong.consult.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.token-validity-in-minutes}")
    private long tokenValidityInMinutes;

    private SecretKey key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMinutes * 60 * 1000);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", role)
                .setExpiration(validity)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String subject = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(subject, token, Collections.singleton(new SimpleGrantedAuthority(claims.get("authorities").toString())));
    }

    public Optional<Claims> validateToken(Optional<String> token) {
        try {
            return token.map(jwt -> {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                return claims;
            });
        } catch (JwtException e) {
            return Optional.empty();
        }
    }
}
package com.bingbong.consult.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Optional<String> token = extractToken(request);
        tokenProvider
                .validateToken(token)
                .ifPresent(claims -> {
                    List<String> authorities = Arrays.asList(claims.get("authorities").toString().split(","));
                    User principal = new User(claims.getSubject(), "", authorities.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));

                    Authentication authn = new UsernamePasswordAuthenticationToken(principal, token, emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authn);
                });

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            return Optional.of(authorizationHeader.substring(BEARER.length()));
        } else {
            return Optional.empty();
        }
    }
}

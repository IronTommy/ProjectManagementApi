package com.example.projects.auth.config;

import com.example.projects.auth.security.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public JwtRequestFilter(JwtTokenUtils jwtTokenUtils, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // log.debug("Method doFilterInternal() started");
        String jwt = extractAccessTokenFromCookie(request);
        // log.debug("JWT token extracted from cookie: {}", jwt);

        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtils.validateToken(jwt)) {
                BearerTokenAuthenticationToken bearerTokenAuthenticationToken = new BearerTokenAuthenticationToken(jwt);
                Authentication authentication = jwtAuthenticationProvider.authenticate(bearerTokenAuthenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // log.error("Token validation failed");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        if (jwt == null) {
            // log.debug("Token not found in request body. Proceeding with filter chain.");
        }

        filterChain.doFilter(request, response);
    }


    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        // log.debug("Method extractAccessTokenFromCookie() started");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    // log.debug("JWT token found in cookie: {}", cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        // log.debug("Token not found in cookies.");
        return null;
    }
}


package com.example.projects.auth.security;

import com.example.projects.auth.config.SecurityConfig;
import com.example.projects.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUserExtractor {

    private final SecurityConfig securityConfig;

    @Deprecated
    public UserDTO getUserFromToken(String token) {
        JwtDecoder jwtDecoder = securityConfig.jwtAccessTokenDecoder();
        Jwt jwt = jwtDecoder.decode(token);

        UUID id = UUID.fromString(jwt.getClaim("sub"));
        String username = jwt.getClaim("firstName");
        String email = jwt.getClaim("email");

        return new UserDTO(id, username, null, email);
    }

    public UserDTO getUserFromAuthentication(Authentication authentication) throws NullPointerException {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID id = UUID.fromString(jwt.getClaim("sub"));
        String username = jwt.getClaim("firstName");
        String email = jwt.getClaim("email");

        return new UserDTO(id, username, null, email);
    }


}
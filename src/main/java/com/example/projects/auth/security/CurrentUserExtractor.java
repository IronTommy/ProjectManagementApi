package com.example.projects.auth.security;

import com.example.projects.dto.user.UserDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrentUserExtractor {


    private final JwtUserExtractor extractor;
    public static JwtUserExtractor staticExtractor;

    @PostConstruct
    private void initExtractor() {
        staticExtractor = extractor;
    }

    public static UserDTO getCurrentUserFromAuthentication() throws NullPointerException {
        log.debug("Method getCurrentUser() started");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return staticExtractor.getUserFromAuthentication(authentication);
    }

    public static UserDTO getUserFromJwt(Jwt jwt) {
        UUID id = UUID.fromString(jwt.getClaim("sub"));
        String username = jwt.getClaim("firstName");
        String email = jwt.getClaim("email");
        return new UserDTO(id, username, null, email);
    }
}
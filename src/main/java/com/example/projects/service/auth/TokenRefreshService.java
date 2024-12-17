package com.example.projects.service.auth;

import com.example.projects.auth.security.CurrentUserExtractor;
import com.example.projects.auth.security.TokenGenerator;
import com.example.projects.dto.user.UserDTO;
import com.example.projects.dto.auth.AuthenticateResponseDto;
import com.example.projects.dto.auth.RefreshDto;
import com.example.projects.entity.user.User;
import com.example.projects.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenRefreshService {

    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;

    public AuthenticateResponseDto refreshToken(RefreshDto refreshTokenDto) {
        log.debug("Method refreshToken started: {}", refreshTokenDto);

        Jwt jwt;
        try {
            jwt = jwtDecoder.decode(refreshTokenDto.getRefreshToken());
        } catch (Exception e) {
            log.error("Token decoding failed: {}", e.getMessage());
            throw new RuntimeException("Token decoding failed");
        }

        if (jwt == null) {
            log.error("Decoded JWT is null.");
            throw new RuntimeException("Decoded JWT is null");
        }

        UserDTO currentUserDTO = CurrentUserExtractor.getUserFromJwt(jwt);
        UUID userId = currentUserDTO.getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found for id: " + userId));

        // Создаем новый access и refresh токены
        AuthenticateResponseDto newToken = tokenGenerator.createToken(user);

        return newToken;
    }
}

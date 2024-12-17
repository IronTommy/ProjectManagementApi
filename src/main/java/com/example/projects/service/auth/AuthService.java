package com.example.projects.service.auth;

import com.example.projects.auth.security.CurrentUserExtractor;
import com.example.projects.auth.security.TokenGenerator;
import com.example.projects.dto.user.UserDTO;
import com.example.projects.dto.auth.AuthenticateDto;
import com.example.projects.dto.auth.AuthenticateResponseDto;
import com.example.projects.dto.auth.RefreshDto;
import com.example.projects.dto.auth.RegistrationDto;
import com.example.projects.entity.user.User;
import com.example.projects.exception.AuthenticationError;
import com.example.projects.repository.user.UserRepository;
import com.example.projects.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRefreshService tokenRefreshService;


    public ResponseEntity<?> createNewUser(RegistrationDto registrationDto) {
        log.debug("Creating new user: {}", registrationDto.getEmail());

        // Приводим email к нижнему регистру
        registrationDto.setEmail(registrationDto.getEmail().toLowerCase());

        User user = userService.createNewUser(registrationDto);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getFirstName(), user.getPassword(), user.getEmail()));
    }

    public ResponseEntity<AuthenticateResponseDto> refreshToken(RefreshDto refreshDto, HttpServletResponse response) {
        log.debug("Received refresh token: {}", refreshDto.getRefreshToken());

        if (refreshDto.getRefreshToken() == null) {
            log.error("Refresh token is null");
            throw new RuntimeException("Refresh token is null");
        }

        AuthenticateResponseDto responseDto = tokenRefreshService.refreshToken(refreshDto);
        saveTokensInCookies(response, responseDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    public ResponseEntity<?> logout(HttpServletResponse response) {

        String userEmail = CurrentUserExtractor.getCurrentUserFromAuthentication().getEmail();
        log.debug("Logging out user: {}", userEmail);

        SecurityContextHolder.clearContext();

        // Удаление куков
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        // accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/"); // Убедитесь, что путь совпадает с путём, когда куки были установлены
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setAttribute("SameSite", "Lax");

        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        // refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/"); // То же самое здесь
        refreshTokenCookie.setMaxAge(0);
        accessTokenCookie.setAttribute("SameSite", "Lax");

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok("Выход из системы успешно выполнен");
    }

    @Transactional
    public ResponseEntity<?> createAuthToken(AuthenticateDto authenticateDto, HttpServletResponse response) {
        log.debug("Logging in user: {}", authenticateDto.getEmail());

        // Приводим email к нижнему регистру для проверки
        String normalizedEmail = authenticateDto.getEmail().toLowerCase();

        // Проверка: существует ли пользователь с данным email
        User user = userService.findByEmail(normalizedEmail);

        if (user == null) {
            // Если пользователя не существует — возвращаем 400 с сообщением о незарегистрированном логине
            return ResponseEntity.badRequest().body(
                    new AuthenticationError(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким логином не зарегистрирован"));
        }

        if (!passwordEncoder.matches(authenticateDto.getPassword(), user.getPassword())) {
            // Если пароль не совпадает — возвращаем 400 с сообщением о неправильном пароле
            return ResponseEntity.badRequest().body(
                    new AuthenticationError(HttpStatus.BAD_REQUEST.value(), "Неправильный пароль"));
        }

        // Если email и пароль правильные, создаем токены
        AuthenticateResponseDto authenticateResponseDto = tokenGenerator.createToken(user);

        // Сохранение токенов в куки
        saveTokensInCookies(response, authenticateResponseDto);

        return ResponseEntity.ok(authenticateResponseDto);
    }


    private void saveTokensInCookies(HttpServletResponse response, AuthenticateResponseDto tokens) {
        // Access Token Cookie
        Cookie accessTokenCookie = new Cookie("accessToken", tokens.getAccessToken());
        // accessTokenCookie.setHttpOnly(true);
        // accessTokenCookie.setSecure(true); // Уберите Secure для отладки, если используете http
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(15 * 60); // 1 минут
        accessTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(accessTokenCookie);

        log.debug("Access token set in cookie: {}", tokens.getAccessToken());

        // Refresh Token Cookie
        Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.getRefreshToken());
        // refreshTokenCookie.setHttpOnly(true);
        // refreshTokenCookie.setSecure(true); // Уберите Secure для отладки, если используете http
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(24 * 60 * 60); // 1 дней
        refreshTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(refreshTokenCookie);

        log.debug("Refresh token set in cookie: {}", tokens.getRefreshToken());

    }


}

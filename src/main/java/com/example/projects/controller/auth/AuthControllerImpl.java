package com.example.projects.controller.auth;

import com.example.projects.dto.auth.*;
import com.example.projects.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    // Показать страницу логина
    @Override
    public String showLoginPage(Model model) {
        return "login"; // Возвращает логин-шаблон
    }

    // Обработать запрос на регистрацию
    @Override
    public ResponseEntity<?> register(RegistrationDto registrationDto) {
        // Логика для регистрации
        return ResponseEntity.ok(authService.createNewUser(registrationDto));
    }

    // Обработать запрос на логин
    @Override
    public ResponseEntity<?> login(AuthenticateDto authenticateDto, HttpServletResponse response) {
        // Логика для аутентификации и создания токена
        return authService.createAuthToken(authenticateDto, response);
    }

    // Выход из системы
    @Override
    public ResponseEntity<?> logout(HttpServletResponse response) throws IOException {
        return authService.logout(response);
    }

    // Обработать refresh токен
    @Override
    public ResponseEntity<AuthenticateResponseDto> refreshToken(RefreshDto refreshDto, HttpServletResponse response) {
        return authService.refreshToken(refreshDto, response);
    }
}

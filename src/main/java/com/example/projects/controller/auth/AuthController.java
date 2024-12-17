package com.example.projects.controller.auth;

import com.example.projects.dto.auth.AuthenticateResponseDto;
import com.example.projects.dto.auth.RefreshDto;
import com.example.projects.dto.auth.RegistrationDto;
import com.example.projects.dto.auth.AuthenticateDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public interface AuthController {

    // Показать страницу логина
    @GetMapping("/login")
    String showLoginPage(Model model);

    // Обработать запрос на регистрацию
    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto);

    // Обработать запрос на логин
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody AuthenticateDto authenticateDto, HttpServletResponse response);

    // Выход из системы
    @PostMapping("/logout")
    ResponseEntity<?> logout(HttpServletResponse response) throws IOException;

    // Обработать refresh токен
    @PostMapping("/refresh")
    ResponseEntity<AuthenticateResponseDto> refreshToken(@RequestBody RefreshDto refreshDto, HttpServletResponse response);
}

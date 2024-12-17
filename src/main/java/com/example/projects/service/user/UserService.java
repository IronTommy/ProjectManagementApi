package com.example.projects.service.user;

import com.example.projects.dto.auth.RegistrationDto;
import com.example.projects.entity.user.User;
import com.example.projects.repository.user.UserRepository;
import com.example.projects.service.account.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;

    @Transactional
    public User createNewUser(RegistrationDto registrationDto) {
        log.debug("Method createNewUser(%s) started with param: \"%s\"".formatted(RegistrationDto.class, registrationDto));
        return userRepository.findById(accountService.createNewAccount(registrationDto).getId()).get();
    }

    public User findByEmail(String email) {
        log.debug("Method findByEmail(%s) started with param: \"%s\"".formatted(String.class, email));

        // Приводим email к нижнему регистру для поиска
        String normalizedEmail = email.toLowerCase();
        return userRepository.findByEmail(normalizedEmail).orElse(null);
    }

}
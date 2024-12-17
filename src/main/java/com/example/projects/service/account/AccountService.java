package com.example.projects.service.account;

import com.example.projects.dto.account.AccountDto;
import com.example.projects.dto.account.AccountUpdateDto;
import com.example.projects.dto.auth.RegistrationDto;
import com.example.projects.entity.account.Account;
import com.example.projects.mapper.account.AccountMapper;
import com.example.projects.repository.account.AccountRepository;
import com.example.projects.service.user.RoleService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AccountDto getById(UUID id) {
        log.debug("Fetching account by ID: {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));
        return accountMapper.entityToDto(account);
    }

    @Transactional
    public AccountDto create(AccountDto dto) {
        log.debug("Creating account with email: {}", dto.getEmail());
        if (accountRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EntityExistsException("Account already exists with email: " + dto.getEmail());
        }
        Account account = accountMapper.dtoToEntity(dto);
        return accountMapper.entityToDto(accountRepository.save(account));
    }

    @Transactional
    public AccountDto update(UUID id, AccountUpdateDto dto) {
        log.debug("Updating account with ID: {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));
        accountMapper.update(account, dto);
        return accountMapper.entityToDto(accountRepository.save(account));
    }

    @Transactional
    public void delete(UUID id) {
        log.debug("Deleting account with ID: {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));
        account.setIsBlocked(true);
        accountRepository.save(account);
    }

    public Account createNewAccount(RegistrationDto registrationDto) {
        log.debug("Creating new account for registration");
        if (accountRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new EntityExistsException("Account already exists with email: " + registrationDto.getEmail());
        }
        Account account = new Account();
        account.setFirstName(registrationDto.getFirstName());
        account.setLastName(registrationDto.getLastName());
        account.setEmail(registrationDto.getEmail());
        account.setPassword(passwordEncoder.encode(registrationDto.getPassword1()));
        account.addRole(roleService.getUserRole());
        return accountRepository.save(account);
    }
}

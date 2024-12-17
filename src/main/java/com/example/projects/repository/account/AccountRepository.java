package com.example.projects.repository.account;

import com.example.projects.entity.account.Account;
import com.example.projects.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends BaseRepository<Account, UUID> {
    Optional<Account> findByEmail(String email);

}
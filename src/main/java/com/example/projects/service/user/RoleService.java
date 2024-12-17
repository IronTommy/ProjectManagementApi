package com.example.projects.service.user;

import com.example.projects.entity.user.Role;
import com.example.projects.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        log.debug("Method getUserRole() started");
        return roleRepository.findByValue("user");
    }
}
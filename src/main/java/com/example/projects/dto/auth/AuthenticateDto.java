package com.example.projects.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateDto {
    private String email;
    private String password;
}


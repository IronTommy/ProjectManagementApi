package com.example.projects.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
public class UserDTO {

    private UUID id;
    private String username;
    private String password;
    private String email;

}

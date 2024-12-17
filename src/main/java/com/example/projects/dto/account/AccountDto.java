package com.example.projects.dto.account;

import com.example.projects.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
}
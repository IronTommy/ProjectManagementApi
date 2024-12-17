package com.example.projects.dto.account;

import com.example.projects.dto.base.BaseSearchDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountSearchDto extends BaseSearchDto {
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isBlocked;
}
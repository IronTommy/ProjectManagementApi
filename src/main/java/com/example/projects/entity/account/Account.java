package com.example.projects.entity.account;

import com.example.projects.entity.user.Role;
import com.example.projects.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Account extends User {

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "is_blocked")
    private Boolean isBlocked;
}
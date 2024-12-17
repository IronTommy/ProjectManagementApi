package com.example.projects.repository.user;

import com.example.projects.entity.user.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByValue(String name);
}
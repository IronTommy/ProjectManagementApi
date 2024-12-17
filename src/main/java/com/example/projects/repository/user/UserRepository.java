package com.example.projects.repository.user;

import com.example.projects.entity.user.User;
import com.example.projects.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findIdByFirstNameIgnoreCase(String authorName);

}
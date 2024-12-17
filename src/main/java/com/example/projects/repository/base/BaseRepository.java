package com.example.projects.repository.base;

import com.example.projects.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, UUID> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
    void delete(T entity);

    T getById(UUID uuid);

    void hardDeleteById(UUID uuid);

    void hardDeleteAll();

}

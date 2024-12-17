package com.example.projects;

import com.example.projects.repository.base.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("")
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)

public class ProjectManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementApiApplication.class, args);
	}

}

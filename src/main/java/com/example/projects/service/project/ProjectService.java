package com.example.projects.service.project;

import com.example.projects.dto.project.ProjectDTO;
import com.example.projects.dto.project.SectionDTO;
import com.example.projects.entity.Project;
import com.example.projects.entity.Section;
import com.example.projects.mapper.project.ProjectMapper;
import com.example.projects.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDto)  // Используем маппер
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Transactional
    public Project createProject(ProjectDTO dto) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setCode(dto.getCode());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setStatus(dto.getStatus());
        return projectRepository.save(project);
    }

    @Transactional
    public Optional<Project> updateProject(Long id, ProjectDTO dto) {
        return projectRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setCode(dto.getCode());
            existing.setStartDate(dto.getStartDate());
            existing.setEndDate(dto.getEndDate());
            existing.setStatus(dto.getStatus());
            return projectRepository.save(existing);
        });
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Transactional
    public Optional<Project> addSection(Long projectId, Section section) {
        return projectRepository.findById(projectId).map(project -> {
            section.setProject(project);
            project.getSections().add(section);
            return projectRepository.save(project);
        });
    }

    @Transactional
    public Optional<List<Section>> getSectionsByProject(Long projectId) {
        return projectRepository.findById(projectId).map(Project::getSections);
    }

    @Transactional
    public Optional<Project> deleteSection(Long projectId, Long sectionId) {
        return projectRepository.findById(projectId).map(project -> {
            project.getSections().removeIf(section -> section.getId().equals(sectionId));
            return projectRepository.save(project);
        });
    }
}

package com.example.projects.controller.project;

import com.example.projects.dto.project.ProjectDTO;
import com.example.projects.entity.Project;
import com.example.projects.entity.Section;
import com.example.projects.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    @Override
    public String homePage(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "index";
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Override
    public ResponseEntity<Project> getProjectById(Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Project createProject(ProjectDTO projectDTO) {
        return projectService.createProject(projectDTO);
    }

    @Override
    public ResponseEntity<Project> updateProject(Long id, ProjectDTO projectDTO) {
        return projectService.updateProject(id, projectDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteProject(Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Project> addSection(Long projectId, Section section) {
        return projectService.addSection(projectId, section)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Section>> getSectionsByProject(Long projectId) {
        return projectService.getSectionsByProject(projectId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Project> deleteSection(Long projectId, Long sectionId) {
        return projectService.deleteSection(projectId, sectionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

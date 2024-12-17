package com.example.projects.controller.project;

import com.example.projects.dto.project.ProjectDTO;
import com.example.projects.entity.Project;
import com.example.projects.entity.Section;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/projects")
public interface ProjectController {


    @GetMapping("/")
    String homePage(Model model);

    @GetMapping
    List<ProjectDTO> getAllProjects();

    @GetMapping("/{id}")
    ResponseEntity<Project> getProjectById(@PathVariable Long id);

    @PostMapping
    Project createProject(@RequestBody ProjectDTO projectDTO);

    @PutMapping("/{id}")
    ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProject(@PathVariable Long id);

    @PostMapping("/{projectId}/sections")
    ResponseEntity<Project> addSection(@PathVariable Long projectId, @RequestBody Section section);

    @GetMapping("/{projectId}/sections")
    ResponseEntity<List<Section>> getSectionsByProject(@PathVariable Long projectId);

    @DeleteMapping("/{projectId}/sections/{sectionId}")
    ResponseEntity<Project> deleteSection(@PathVariable Long projectId, @PathVariable Long sectionId);
}

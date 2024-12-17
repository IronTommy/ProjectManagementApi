package com.example.projects.dto.project;

import com.example.projects.entity.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProjectDTO {
    private Long id;
    private String name;
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private List<SectionDTO> sections;

    public ProjectDTO(Long id, String name, String code, LocalDate startDate,
                      LocalDate endDate, ProjectStatus status, List<SectionDTO> sections
                    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.sections = sections;
    }
}

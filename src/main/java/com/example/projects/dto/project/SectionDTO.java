package com.example.projects.dto.project;

public class SectionDTO {
    private Long id;
    private String name;

    public SectionDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}

package com.example.projects.mapper.project;

import com.example.projects.dto.project.ProjectDTO;
import com.example.projects.dto.project.SectionDTO;
import com.example.projects.entity.Project;
import com.example.projects.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    // Маппинг из Project в ProjectDTO
    @Mapping(target = "sections", source = "sections")
    ProjectDTO toDto(Project project);

    // Маппинг из Section в SectionDTO
    SectionDTO toSectionDto(Section section);

    // Маппинг списка Section в SectionDTO
    List<SectionDTO> toSectionDtoList(List<Section> sections);

    // Маппинг из DTO в Entity
    Project toEntity(ProjectDTO projectDTO);
}
